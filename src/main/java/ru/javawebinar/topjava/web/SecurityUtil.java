package ru.javawebinar.topjava.web;

import static ru.javawebinar.topjava.util.MealsUtil.DEFAULT_CALORIES_PER_DAY;

public class SecurityUtil {

    private static Integer authorizedUserId = 1;

    public static int authUserId() {
        return authorizedUserId;
    }

    public static void setAuthorizedUserId(Integer authorizedUserId) {
        SecurityUtil.authorizedUserId = authorizedUserId;
    }

    public static int authUserCaloriesPerDay() {
        return DEFAULT_CALORIES_PER_DAY;
    }
}