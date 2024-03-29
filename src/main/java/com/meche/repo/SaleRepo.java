package com.meche.repo;

import com.meche.model.InvoiceSale;
import com.meche.model.Sale;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Month;
import java.time.Year;
import java.util.List;

/**
 * @Author sidof
 * @Since 20/05/2023
 */

public interface SaleRepo extends JpaRepository<Sale, Long> {
    @Override
    @Query("SELECT i FROM Sale i  ORDER BY i.id desc ")
    List<Sale> findAll();

    @Query("SELECT i FROM Sale i WHERE i.month = :month AND i.year = :year ")
    List<Sale> findByMonthAndYear(@Param("month") Month month, @Param("year") Year year, Sort sort);
    @Query("SELECT i FROM Sale i WHERE i.day = :day AND i.month = :month ")
    List<Sale> findByDayAndMonth(@Param("day") int day,@Param("month") Month month,Sort sort);

    List<Sale> findByDayAndMonth(int day,Month month);
    @Query("SELECT i FROM Sale i WHERE i.month = ?1 ")
    List<Sale>findByMonth(Month month);
    @Query("SELECT i FROM Sale i WHERE i.day = ?1 ")
    List<Sale>findByDay(int day);


}
