package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.springframework.data.domain.Sort.by;

@Repository
public class DataJpaMealRepository implements MealRepository {
    private static final Sort SORT_BY_DATE_TIME = by(Sort.Direction.DESC, "datetime");
    @Autowired
    private CrudMealRepository meals;
    @Autowired
    private CrudUserRepository users;

    @Override
    public Meal save(Meal meal, int userId) {
        if (!meal.isNew() && get(meal.getId(), userId) == null) {
            return null;
        }

        meal.setUser(users.getOne(userId));
        return meals.save(meal);
    }

    @Override
    public boolean delete(int id, int userId) {
       return meals.deleteByIdAndUserId(id, userId) != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        return meals.getByIdAndUserId(id, userId);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return meals.getAllByUserId(userId);
    }

    @Override
    public List<Meal> getBetweenInclusive(LocalDate startDate, LocalDate endDate, int userId) {
        return meals.findByDateTimeBetweenAndUserId(startDate, endDate, userId, SORT_BY_DATE_TIME);
    }
}
