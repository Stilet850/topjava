package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.util.List;

public interface MealRepository {
    // null if not found, when updated
    Meal save(Meal meal, int userId);

    // false if not found
    boolean delete(int mealId, int userId);

    // null if not found
    Meal get(int mealId, int userId);

    List<Meal> getAllBy(int userId);

    List<Meal> getAllBy(int userId, LocalDate startDate, LocalDate endDate);
}
