package com.meche.repo;

import com.meche.model.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author sidof
 * @Since 20/05/2023
 */

public interface PurchaseRepo extends JpaRepository<Purchase, Long> {
}
