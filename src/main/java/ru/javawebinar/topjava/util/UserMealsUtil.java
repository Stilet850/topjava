package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.time.LocalDateTime.of;
import static java.time.LocalTime.of;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.summingInt;
import static java.util.stream.Collectors.toList;
import static ru.javawebinar.topjava.util.TimeUtil.isBetween;

public class UserMealsUtil {

    public static final List<UserMeal> MEAL_LIST = asList(
            new UserMeal(of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
            new UserMeal(of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
            new UserMeal(of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
            new UserMeal(of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
            new UserMeal(of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
            new UserMeal(of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
    );
    public static final int CALORIES_PER_DAY = 2000;

    public static void main(String[] args) {
        getFilteredWithExceeded(MEAL_LIST, of(7, 0), of(12, 0), CALORIES_PER_DAY).forEach(System.out::println);
        System.out.println("Using streams: ");
        getFilteredWithExceededUseStreams(MEAL_LIST, of(7, 0), of(12, 0), CALORIES_PER_DAY).forEach(System.out::println);
    }

    public static List<UserMealWithExceed> getFilteredWithExceededUseStreams(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, Integer caloriesPerDay) {
        Map<LocalDate, Integer> allCaloriesPerDay =
                mealList.stream()
                        .collect(Collectors.groupingBy(meal -> meal.getDateTime().toLocalDate(), summingInt(UserMeal::getCalories)));

        List<UserMealWithExceed> userMealWithExceeds =
                mealList.stream()
                        .filter(meal -> isBetween(meal.getDateTime().toLocalTime(), startTime, endTime)).map(meal -> createUserMealWithExceed(meal, caloriesPerDay < allCaloriesPerDay.get(meal.getDateTime().toLocalDate()))).collect(toList());

        return userMealWithExceeds;
    }

    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> allCaloriesPerDay = new HashMap<>();
        for (UserMeal meal : mealList) {
            allCaloriesPerDay.merge(meal.getDateTime().toLocalDate(), meal.getCalories(), Integer::sum);
        }
        List<UserMealWithExceed> userMealWithExceeds = new ArrayList<>();

        for (UserMeal meal : mealList) {
            LocalDateTime localDateTime = meal.getDateTime();
            if (isBetween(localDateTime.toLocalTime(), startTime, endTime)) {
                userMealWithExceeds.add(createUserMealWithExceed(meal, caloriesPerDay < allCaloriesPerDay.get(meal.getDateTime().toLocalDate())));
            }
        }
        return userMealWithExceeds;
    }

    private static UserMealWithExceed createUserMealWithExceed(UserMeal meal, boolean exceed) {
        return new UserMealWithExceed(meal.getDateTime(), meal.getDescription(), meal.getCalories(), exceed);
    }
}
