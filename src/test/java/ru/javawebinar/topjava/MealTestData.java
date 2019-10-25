package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import static java.time.LocalDateTime.of;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int USER_ID = 100000;

    public static final int FIRST_MEAL_ID_OF_FIRST_USER_ID = START_SEQ + 2;
    public static final int SEC_MEAL_ID_OF_FIRST_USER_ID = START_SEQ + 3;
    public static final int THIRD_MEAL_ID_OF_FIRST_USER_ID = START_SEQ + 4;
    public static final int FIRST_MEAL_ID_OF_SEC_USER_ID = START_SEQ + 5;
    public static final int SEC_MEAL_ID_OF_SEC_USER_ID = START_SEQ + 6;
    public static final int THIRD_MEAL_ID_OF_SEC_USER_ID = START_SEQ + 7;

    public static final Meal NEW_MEAL = new Meal(null, of(2019, 11, 25, 9, 00, 00, 0), "Breakfast", 700);
    public static final Meal MEAL_1_OF_FIRST_USER = new Meal(FIRST_MEAL_ID_OF_FIRST_USER_ID, of(2019, 10, 24, 9, 00, 00, 0), "Breakfast", 500);
    public static final Meal MEAL_1_OF_FIRST_USER_TO_UPDATE = new Meal(FIRST_MEAL_ID_OF_FIRST_USER_ID, of(2019, 10, 24, 15, 00, 00, 0), "Breakfast", 500);
    public static final Meal MEAL_2_OF_FIRST_USER = new Meal(SEC_MEAL_ID_OF_FIRST_USER_ID, of(2019, 10, 24, 14, 00, 00, 0), "Lunch", 1000);
    public static final Meal MEAL_3_OF_FIRST_USER = new Meal(THIRD_MEAL_ID_OF_FIRST_USER_ID, of(2019, 10, 24, 19, 00, 00, 0), "Supper", 510);
    public static final Meal MEAL_1_OF_SECON_USER = new Meal(FIRST_MEAL_ID_OF_SEC_USER_ID, of(2019, 10, 24, 9, 00, 00, 0), "Breakfast", 500);
    public static final Meal MEAL_2_OF_SECON_USER = new Meal(SEC_MEAL_ID_OF_SEC_USER_ID, of(2019, 10, 24, 14, 00, 00, 0), "Lunch", 1000);
    public static final Meal MEAL_3_OF_SECON_USER = new Meal(THIRD_MEAL_ID_OF_SEC_USER_ID, of(2019, 10, 24, 19, 00, 00, 0), "Supper", 500);

    public static final Meal[] SORTED_MEAL_OF_FIRST_USER = {
            MEAL_3_OF_FIRST_USER, MEAL_2_OF_FIRST_USER, MEAL_1_OF_FIRST_USER};

 public static final Meal[] SORTED_MEAL_OF_SECON_USER = {
            MEAL_3_OF_SECON_USER, MEAL_2_OF_SECON_USER, MEAL_1_OF_SECON_USER};
    public static final int ADMIN_ID = 100001;


    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualToComparingFieldByField(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingFieldByFieldElementComparator().isEqualTo(expected);
    }
}
