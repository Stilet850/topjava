package ru.javawebinar.topjava.service.jdbc;

import ru.javawebinar.topjava.service.AbstractMealServiceTest;
import ru.javawebinar.topjava.Profiles;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles(Profiles.JDBC)
public class JdbcMealServiceTest extends AbstractMealServiceTest {
}