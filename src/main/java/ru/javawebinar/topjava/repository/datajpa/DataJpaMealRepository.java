package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;

import static org.springframework.data.domain.Sort.by;
import static ru.javawebinar.topjava.util.DateTimeUtil.*;

@Repository
public class DataJpaMealRepository implements MealRepository {
    private static final Sort SORT_BY_DATE_TIME = by(Sort.Direction.DESC, "dateTime");
    @Autowired
    private CrudMealRepository meals;
    @Autowired
    private CrudUserRepository users;

    @Override
    @Transactional
    public Meal save(Meal meal, int userId) {
        if (!meal.isNew() && get(meal.getId(), userId) == null) {
            return null;
        }

        meal.setUser(users.getOne(userId));
        return meals.save(meal);
    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {
       return meals.deleteByIdAndUserId(id, userId) != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        return meals.getByIdAndUserId(id, userId);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return meals.getAllByUserId(userId, SORT_BY_DATE_TIME);
    }

    @Override
    public List<Meal> getBetweenInclusive(LocalDate startDate, LocalDate endDate, int userId) {
        return meals.findByDateAndUserId(getStartInclusive(startDate), getEndExclusive(endDate), userId);
    }
}
