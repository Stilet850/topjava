package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Transactional(readOnly = true)
public interface CrudMealRepository extends JpaRepository<Meal, Integer> {
    int deleteByIdAndUserId(int id, int userId);

    Meal getByIdAndUserId(int id, int userId);

    List<Meal> getAllByUserId(int userId, Sort sort);

    @Query("SELECT m FROM Meal m WHERE m.dateTime >= :startDate AND m.dateTime <:endDate AND m.user.id = :userId ORDER BY m.dateTime DESC ")
    List<Meal> findByDateAndUserId(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate, @Param("userId")int userId);

    List<Meal> findByDateTimeGreaterThanEqualAndDateTimeLessThanAndUserId(LocalDateTime startDate, LocalDateTime endDate, int userId, Sort sortByDateTime);
}
