package com.sidof.service.interfaceService;

import com.meche.model.Inventory;

import java.util.List;
import java.util.Optional;

/**
 * @Author sidof
 * @Since 20/05/2023
 */
public interface InventoryDao {
    Inventory addInventory(Inventory inventory);

    List<Inventory> addInventoryForSale(List<Inventory> inventory);

    Inventory updateInventory(Inventory inventory);

    Inventory INVENTORY(Long inventoryId);

    Optional<Inventory> INVENTORY_OPTIONAL(String productName, boolean isUp);

    List<Inventory> INVENTORIES();

    List<Inventory> listInventoryByName(String productName);

    boolean inventoryByName(String productName);

    Boolean inventoryById(Long productId);


}
