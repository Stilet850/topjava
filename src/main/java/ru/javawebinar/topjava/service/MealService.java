package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealService {
    private final MealRepository repository;

    @Autowired
    public MealService(MealRepository repository) {
        this.repository = repository;
    }

    public Meal create(Meal meal, int userId) {
        return checkNotFoundWithId(repository.save(meal, userId), userId);
    }

    public void delete(int mealId, int userId) throws NotFoundException {
        checkNotFoundWithId(repository.delete(mealId, userId), userId);
    }

    public Meal get(int mealId, int userId) throws NotFoundException {
        return checkNotFoundWithId(repository.get(mealId, userId), userId);
    }

    public Meal update(Meal meal, int userId) {
        return checkNotFoundWithId(repository.save(meal, userId), userId);
    }

    public List<Meal> getAllBy(int userId) {
        return repository.getAllBy(userId);
    }

    public List<Meal> getAllBy(int userId, LocalDate startDate, LocalDate endDate) {
        return repository.getAllBy(userId, startDate, endDate);
    }
}