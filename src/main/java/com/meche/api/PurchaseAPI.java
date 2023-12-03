package com.meche.api;

import com.meche.api.operation.InventoryOperation;
import com.meche.api.operation.Utils;
import com.meche.model.Inventory;
import com.meche.model.Product;
import com.meche.model.Purchase;
import com.meche.model.Transaction;
import com.meche.service.InventoryService;
import com.meche.service.ProductService;
import com.meche.service.PurchaseService;
import com.meche.service.TransactionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.Year;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.meche.model.enume.Status.PAID;
import static com.meche.model.enume.Status.PENDING;
import static com.meche.model.enume.TransactionType.PURCHASE;
import static java.time.LocalDate.now;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

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
@Transactional
public class PurchaseAPI {

    private final PurchaseService purchaseService;
    private final ProductService productService;
    private final InventoryOperation inventoryOperation;
    private final TransactionService transactionService;
    private final InventoryService inventoryService;

    @GetMapping
    public ResponseEntity<List<Purchase>> getPurchases()
            throws InterruptedException {
        List<Purchase> purchases = purchaseService.PURCHASES();
        TimeUnit.SECONDS.sleep(3);
        return new ResponseEntity<>(purchases, OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Purchase> getPurchase(@PathVariable("id") Long id) {
        return new ResponseEntity<Purchase>(purchaseService.getPurchase(id), OK);
    }

    @PutMapping
    public ResponseEntity<Purchase> edit(@RequestBody Purchase purchaseToSave)
            throws InterruptedException {
        purchaseToSave.setStatus(PAID);
        TimeUnit.SECONDS.sleep(1);
        return new ResponseEntity<>(purchaseService.updatePurchase(purchaseToSave), CREATED);
    }

    /**
     * First Get Stock List of Product
     * After buy we need to set the sale price
     * Returns the day of the week represented by this date.
     * The returned value (0 = Sunday, 1 = Monday, 2 = Tuesday,
     * 3 = Wednesday, 4 = Thursday, 5 = Friday, 6 = Saturday)
     */
    @PostMapping
    public ResponseEntity<Purchase> savePurchase(@RequestBody Purchase purchaseToSave) throws InterruptedException {
        purchaseToSave.setPurchaseAt(now());
        purchaseToSave.setYear(Year.now());
        purchaseToSave.setMonth(now().getMonth());
        purchaseToSave.setDay(new Date().getDay());
        purchaseToSave.setStatus(PENDING);
        final int quantity = purchaseToSave.getQuantity();
        final double price = purchaseToSave.getPrice();
        final double amount = (double) quantity * price;
        purchaseToSave.setAmount(amount);


        var transaction =new  Transaction(
                null,
                Utils.generateTransactionId(),
                amount,
                "UNIVERSAL MECHE",
                purchaseToSave.getSupplier().getName(),
                LocalDateTime.now(),
                PURCHASE,
                new ArrayList<>(),
                new ArrayList<>()
        );
        Transaction transactionSaved = transactionService.save(transaction);
        purchaseToSave.setTransaction(transactionSaved);
        Purchase purchaseSaved = purchaseService.addPurchase(purchaseToSave);
        final Inventory inventorySave = inventoryOperation.cmupForPurchase("PURCHASE", purchaseSaved);
        inventoryService.saveInventory(inventorySave);
//      OPTIONAL
        Product purchaseSavedProduct = purchaseSaved.getProduct();
        purchaseSavedProduct.setSalePrice(purchaseSaved.getSalePrice());
//        updateSalePrice(purchaseSavedProduct);
        TimeUnit.SECONDS.sleep(1);
        return new ResponseEntity<Purchase>(purchaseSaved, CREATED);
    }

    @PutMapping("/updateSalePrice")
    private void updateSalePrice(@RequestBody Product purchaseSavedProduct) {
        productService.updateProduct(purchaseSavedProduct);
    }


}
