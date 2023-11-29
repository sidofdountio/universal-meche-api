package com.sidof.repo;

import com.meche.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * @Author sidof
 * @Since 20/05/2023
 */
public interface InventoryRepo extends JpaRepository<Inventory, Long> {
    @Query(value = "SELECT u FROM Inventory u")
    List<Inventory> findAllInventoryList();

    @Override
    @Query("SELECT i FROM Inventory i  ORDER BY id")
    List<Inventory> findAll();

    Optional<Inventory> findByProductNameAndUp(String productName, boolean isUp);

    List<Inventory> findAllByProductName(String productName);

    Optional<Inventory> findInventoryByProductName(String productName);

}
