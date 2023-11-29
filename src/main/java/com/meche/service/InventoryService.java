package com.meche.service;

import com.meche.model.Inventory;
import com.meche.model.InvoiceSale;
import com.sidof.repo.InventoryRepo;
import com.sidof.service.interfaceService.InventoryDao;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Month;
import java.time.Year;
import java.util.List;
import java.util.Optional;

/**
 * @Author sidof
 * @Since 20/05/2023
 * @Version v1.0
 */
@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class InventoryService implements InventoryDao {

    private InventoryRepo inventoryRepo;

    public InventoryService(InventoryRepo inventoryRepo) {
        this.inventoryRepo = inventoryRepo;
    }

    @Override
    public List<Inventory> addInventoryForSale(List<Inventory> inventoryToSave) {
        log.info("inventory saved {}", inventoryToSave);
        return inventoryRepo.saveAll(inventoryToSave);
    }

    @Override
    public Inventory addInventory(Inventory inventory) {
        return inventoryRepo.save(inventory);
    }

    @Override
    public Inventory updateInventory(Inventory inventory) {
        return inventoryRepo.save(inventory);
    }

    @Override
    public Inventory INVENTORY(Long inventoryId) {
        final boolean existsById = inventoryRepo.existsById(inventoryId);
        if (!existsById) {
            throw new IllegalStateException("Invotory with this id " + inventoryId + " exist");
        }
        return inventoryRepo.findById(inventoryId).get();
    }

    @Override
    public Optional<Inventory> INVENTORY_OPTIONAL(String productName, boolean isUp) {
        Optional<Inventory> optionalInventory = inventoryRepo.findByProductNameAndUp(productName, isUp);
        if (!optionalInventory.isPresent()) {
            log.info("No up inventory with this {}", productName);
            throw new IllegalStateException("No up inventory with this " + productName);
        }
        log.info("Fecthing optional up inventory {}", productName);
        return optionalInventory;
    }

    @Override
    public List<Inventory> INVENTORIES() {
        log.info("Fetching Inventory ...");
        return inventoryRepo.findAll();
    }

    @Override
    public List<Inventory> listInventoryByName(String productName) {
        return null;
    }

    @Override
    public boolean inventoryByName(String productName) {
        boolean present = inventoryRepo.findInventoryByProductName(productName).isPresent();
        if (!present) {
            throw new IllegalStateException("Product Not exist");
        }
        return present;
    }

    @Override
    public Boolean inventoryById(Long productId) {
        boolean existsById = inventoryRepo.existsById(productId);
        if (!existsById) {
            throw new IllegalStateException("");
        }
        return existsById;
    }


}
