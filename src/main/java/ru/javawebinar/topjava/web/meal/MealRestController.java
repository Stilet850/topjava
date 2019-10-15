package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

@Controller
public class MealRestController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    private final MealService service;
    private final int CALORIES = MealsUtil.DEFAULT_CALORIES_PER_DAY;

    @Autowired
    public MealRestController(MealService service) {
        this.service = service;
    }

    public List<MealTo> getAll() {
        log.info("getAll");
        return MealsUtil.getTos(service.getAll(), CALORIES);
    }

    public List<MealTo> getAllById(int id) {
        log.info("getAllById");
        return MealsUtil.getTos(service.getAllByUserId(id), CALORIES);
    }

    public List<MealTo> sortBy(LocalTime start, LocalTime end, int id) {
        log.info("sortBy Time");
        return MealsUtil.getFilteredTos(service.getAllByUserId(id), CALORIES, start, end);
    }

    public List<MealTo> sortBy(LocalDate start, LocalDate end, int id) {
        log.info("sortBy Date");
        return MealsUtil.getFilteredTos(service.getAllByUserId(id), CALORIES, start, end);
    }

    public List<MealTo> sortBy(LocalDateTime first, LocalDateTime end, int id) {
        log.info("sortBy DateTime");
        return MealsUtil.getFilteredTos(service.getAllByUserId(id), CALORIES, first, end);
    }

    public Meal create(Meal meal) {
        log.info("create {}", meal);
        checkNew(meal);
        return service.create(meal);
    }

    public void delete(int id, int userId) {
        log.info("delete {}", id);
        service.delete(id, userId);
    }

    public void update(Meal meal, int userId) {
        log.info("update {} with id={}", meal, meal.getId());
        service.update(meal, userId);
    }

    public Meal get(int id, int userId) {
        log.info("get {} with id={}", id);
        return service.get(id, userId);
    }
}