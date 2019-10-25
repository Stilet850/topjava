package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.time.LocalDate.of;
import static java.util.Arrays.asList;
import static ru.javawebinar.topjava.MealTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {
    @Autowired
    private MealService service;
    @Autowired
    private DataSource dataSource;
    private static boolean isInitialized;

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Test
    public void get() {
        assertMatch(service.get(FIRST_MEAL_ID_OF_FIRST_USER_ID, USER_ID), MEAL_1_OF_FIRST_USER);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFoundException() {
        assertMatch(service.get(FIRST_MEAL_ID_OF_FIRST_USER_ID, ADMIN_ID), MEAL_1_OF_FIRST_USER);
    }

    @Test
    public void delete() {
        List<Meal> meals = new ArrayList<>(asList(SORTED_MEAL_OF_FIRST_USER));
        meals.remove(2);
        int userId = USER_ID;
        service.delete(FIRST_MEAL_ID_OF_FIRST_USER_ID, userId);
        assertMatch(service.getAll(userId), meals);
    }

    @Test(expected = NotFoundException.class)
    public void deleteNotFoundException() {
        service.delete(FIRST_MEAL_ID_OF_FIRST_USER_ID, ADMIN_ID);
    }

    @Test
    public void getBetweenDates() {
        LocalDate startDate = of(2019, 10, 24);
        LocalDate endDate = of(2019, 10, 25);

        List<Meal> meals = service.getBetweenDates(startDate, endDate, USER_ID);
        assertMatch(meals, SORTED_MEAL_OF_FIRST_USER);
    }

    @Test
    public void getAll() {
        assertMatch(service.getAll(USER_ID), SORTED_MEAL_OF_FIRST_USER);
    }

    @Test
    public void update() {
        Meal mealToUpdate = MEAL_1_OF_FIRST_USER_TO_UPDATE;
        int userId = USER_ID;
        service.update(mealToUpdate, userId);
        assertMatch(service.get(mealToUpdate.getId(), userId), mealToUpdate);
    }

    @Test(expected = NotFoundException.class)
    public void updateNotFoundException() {
        service.update(MEAL_1_OF_FIRST_USER_TO_UPDATE, ADMIN_ID);
    }

    @Test
    public void create() {
        Meal mealToCreate = NEW_MEAL;
        int userId = USER_ID;
        service.create(mealToCreate, userId);
        assertMatch(service.get(mealToCreate.getId(), userId), NEW_MEAL);
    }
}