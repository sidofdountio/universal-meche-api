package com.meche.api;

import com.meche.model.Inventory;
import com.meche.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

/**
 * @Author sidof
 * @Since 20/05/2023
 * @Version v1.0
 */
@RestController
@RequestMapping("/api/v1/hair/inventory")
@CrossOrigin(origins = "http://localhost:4200/", maxAge = 3600)
@RequiredArgsConstructor
public class InventoryApi {
    private final InventoryService inventoryService;

    @GetMapping
    public ResponseEntity<List<Inventory>> getInventory() throws InterruptedException {
        List<Inventory> inventories = inventoryService.getInventories(Sort.by("id"));

        return new ResponseEntity<List<Inventory>>(inventories, OK);
    }


}