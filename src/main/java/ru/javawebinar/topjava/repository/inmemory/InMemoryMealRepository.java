package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryMealRepository.class);
    public static final Comparator<Meal> MEAL_COMPARATOR = comparing(Meal::getDateTime).reversed();
    private Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.getTestData().forEach(meal -> save(meal, 1));
        MealsUtil.getTestData().forEach(meal -> save(meal, 2));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        meal.setUserId(userId);

        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            getMapBy(userId).put(meal.getId(), meal);
            return meal;
        }

        log.info("userId {} save {} ", userId, meal);
        // treat case: update, but not present in storage
        return getMapBy(userId).computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int mealId, int userId) {
        log.info("userId {} delete {} ", userId, mealId);
        return repository.get(userId).remove(mealId) != null;
    }

    @Override
    public Meal get(int mealId, int userId) {
        log.info("userId {} get {} ", userId, mealId);
        return repository.values().stream().flatMap(map -> map.entrySet().stream()).filter(mealEntry -> mealEntry.getKey().equals(mealId)).map(Map.Entry::getValue).findFirst().get();
    }

    @Override
    public List<Meal> getAllBy(int userId) {
        log.info("getAllBy {}", userId);
        return getMapBy(userId).values().stream().sorted(MEAL_COMPARATOR).collect(toList());
    }

    @Override
    public List<Meal> getAllBy(int userId, LocalDate startDate, LocalDate endDate) {
        log.info("getAllBy userId {} startDate {} endDate {} ", userId, startDate, endDate);
        return getMapBy(userId).values().stream().filter(meal -> DateTimeUtil.isBetween(meal.getDate(), startDate, endDate)).sorted(MEAL_COMPARATOR).collect(toList());
    }

    private Map<Integer, Meal> getMapBy(int userId) {
        log.info("getMapBy userId {} ", userId);
        Map<Integer, Meal> meals = repository.putIfAbsent(userId, new HashMap<>());
        if (meals == null) {
            meals = repository.get(userId);
        }

        return meals;
    }
}

