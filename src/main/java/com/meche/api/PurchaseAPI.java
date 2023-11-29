package com.meche.api;

import com.meche.api.operation.InventoryOperation;
import com.meche.api.operation.Utils;
import com.meche.model.Inventory;
import com.meche.model.Purchase;
import com.meche.model.Transaction;
import com.meche.service.InventoryService;
import com.meche.service.ProductService;
import com.meche.service.PurchaseService;
import com.meche.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.Year;
import java.util.Date;
import java.util.List;

import static com.meche.model.enume.Status.PENDING;
import static com.meche.model.enume.TransactionType.PURCHASE;
import static java.time.LocalDate.now;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.CREATED;

/**
 * @Author sidof
 * @Since 28/11/2023
 * @Version v1.0
 * @YouTube @sidof8065
 */
@RestController
@RequestMapping("/api/v1/hair/purchase")
@CrossOrigin(origins = "http://localhost:4200/", maxAge = 3600)
@RequiredArgsConstructor
public class PurchaseAPI {

    private final PurchaseService purchaseService;
    private final InventoryService inventoryService;
    private final ProductService productService;
    private final InventoryOperation inventoryOperation;
    private final TransactionService transactionService;

    @GetMapping
    public ResponseEntity<List<Purchase>> getPurchases() {
        return new ResponseEntity<>(purchaseService.PURCHASES(), OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Purchase> getPurchase(@PathVariable("id")Long id) {
        return new ResponseEntity<Purchase>(purchaseService.getPurchase(id), OK);
    }

    @PutMapping
    public ResponseEntity<Purchase> edit(@RequestBody Purchase purchaseToSave) {
        return new ResponseEntity<>(purchaseService.updatePurchase(purchaseToSave),CREATED);
    }


    /**
     * First Get Stock List of Product
     * After buy we need to set the sale price
     * Returns the day of the week represented by this date.
     * The returned value (0 = Sunday, 1 = Monday, 2 = Tuesday,
     * 3 = Wednesday, 4 = Thursday, 5 = Friday, 6 = Saturday)
     */
    @PostMapping
    public ResponseEntity<Purchase> savePurchase(@RequestBody Purchase purchaseToSave) {
        List<Inventory> inventoryList = inventoryService.INVENTORIES();

        purchaseToSave.setPurchaseAt(now());
        purchaseToSave.setYear(Year.now());
        purchaseToSave.setMonth(now().getMonth());
        purchaseToSave.setDay(new Date().getDay());
        purchaseToSave.setStatus(PENDING);
        final int quantity = purchaseToSave.getQuantity();
        final double price = purchaseToSave.getPrice();
        final double amount = (double) quantity * price;
        purchaseToSave.setAmount(amount);

        var transaction = Transaction.builder()
                .amount(amount)
                .id(null)
                .timestamp(LocalDateTime.now())
                .type(PURCHASE)
                .sender("UNIVERSAL MECHE")
                .receiver(purchaseToSave.getSupplier().getName())
                .trsansactionId(Utils.generateTransactionId())
                .build();
        Transaction transactionSaved = transactionService.save(transaction);
        purchaseToSave.setTransaction(transactionSaved);
        Purchase purchase = purchaseService.addPurchase(purchaseToSave);
        Inventory inventorySave = inventoryOperation.cmupForPurchase("PURCHASE", purchase, inventoryList);
        inventoryService.addInventory(inventorySave);
//      OPTIONAL
//        var updateProductSalePrice = Product.builder()
//                .salePrice(purchase.getSalePrice())
//                .id(purchase.getProduct().getId())
//                .build();
//        productService.updateProduct(updateProductSalePrice);

        return new ResponseEntity<Purchase>(purchase, CREATED);
    }

}

