package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.ValidationUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.DateTimeUtil.getValue;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserCaloriesPerDay;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

@Controller
public class MealRestController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    private final MealService service;

    @Autowired
    public MealRestController(MealService service) {
        this.service = service;
    }

    public List<MealTo> getAll() {
        log.info("getAll");
        return MealsUtil.getTos(service.getAllBy(authUserId()), authUserCaloriesPerDay());
    }

    public List<MealTo> filterBy(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        log.info("filterBy startDate {}, endDate{}, startTime{}, endTime{} ", startDate, endDate, startTime, endTime);

        final List<Meal> filteredByDate = service.getAllBy(authUserId(), getValue(startDate, LocalDate.MIN), getValue(endDate, LocalDate.MAX));

        return MealsUtil.getFilteredTos(filteredByDate, authUserCaloriesPerDay(), getValue(startTime, LocalTime.MIN), getValue(endTime, LocalTime.MAX));
    }

    public Meal create(Meal meal) {
        log.info("create {}", meal);
        checkNew(meal);
        return service.create(meal, authUserId());
    }

    public void delete(int mealId) {
        log.info("delete {}", mealId);
        service.delete(mealId, authUserId());
    }

    public void update(Meal meal, int mealId) {
        log.info("update {} with id={}", meal, meal.getId());
        ValidationUtil.assureIdConsistent(meal, mealId);
        service.update(meal, authUserId());
    }

    public Meal get(int mealId) {
        log.info("get {}", mealId);
        return service.get(mealId, authUserId());
    }
}