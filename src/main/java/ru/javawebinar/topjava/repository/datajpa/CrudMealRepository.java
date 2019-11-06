package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface CrudMealRepository extends JpaRepository<Meal, Integer> {
    int deleteByIdAndUserId(int id, int userId);

    Meal getByIdAndUserId(int id, int userId);

    List<Meal> getAllByUserId(int userId, Sort sort);

    List<Meal> findByDateTimeGreaterThanEqualAndDateTimeLessThanAndUserId(LocalDateTime startDate, LocalDateTime endDate, int userId, Sort sortByDateTime);
}
