package com.meche.api;

import com.meche.model.Inventory;
import com.meche.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

/**
 * @Author sidof
 * @Since 20/05/2023
 * @Version v1.0
 */
@RestController
@RequestMapping("/api/v1/hair/inventory")
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:80"},maxAge = 3600,allowedHeaders = "*")
@RequiredArgsConstructor
public class InventoryApi {
    private final InventoryService inventoryService;

    @GetMapping
    public ResponseEntity<List<Inventory>> getInventory() {
        final List<Inventory> inventories = inventoryService.INVENTORIES();
        return new ResponseEntity<>(inventories, OK);
    }

    @PostMapping("/addInventory")
    public ResponseEntity<Inventory> saveInventory(@RequestBody Inventory inventoryTosave) {
        var inventory = inventoryService.addInventory(inventoryTosave);
        return new ResponseEntity<>(inventory, CREATED);
    }

    @PutMapping("/updateInventory")
    public ResponseEntity<Inventory> UpdateInventory(@RequestBody Inventory inventoryToEdit) {
        var inventory = inventoryService.addInventory(inventoryToEdit);
        return new ResponseEntity<>(inventory, CREATED);
    }
}
