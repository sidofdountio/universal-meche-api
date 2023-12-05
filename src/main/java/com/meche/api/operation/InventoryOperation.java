package com.meche.api.operation;

import com.meche.model.Inventory;
import com.meche.model.Product;
import com.meche.model.Purchase;
import com.meche.model.Sale;
import com.meche.service.InventoryService;
import com.meche.service.ProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.time.LocalDate.now;

/**
 * @Author sidof
 * @Since 28/11/2023
 * @Version v1.0
 * @YouTube @sidof8065
 */
@Transactional
@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryOperation implements StockManager {

    private final ProductService productService;
    private final InventoryService inventoryService;

    /**
     * In this project the new price (That correspond for sale price)
     * Will be provide by the owner or mananer.
     * <p>
     * We check up product on our stock.
     * Up product its doeat who have been save laters(Purchase and sale)
     * and the quantity still great thant O.
     * We also check if the provide product exist on our stork or database.
     * In this case the newPrice attribute  will provider.
     */
    @Override
    public Inventory cmupForPurchase(String label, Purchase purchase) {

        List<Inventory> inventoryList = inventoryService.INVENTORY_LIST();
        final Product productById = productService.getProductById(purchase.getProduct().getId());
        int day = LocalDate.now().getDayOfMonth();
        Month month = LocalDate.now().getMonth();
        Year year = Year.now();
        final int quantity = purchase.getQuantity();
        final double price = purchase.getPrice();
        final double amount = quantity * price;

        final int newQuantity = purchase.getQuantity();
        final double newPrice = purchase.getSalePrice();
        final double newAmount = purchase.getAmount();
        Inventory inventoryToPucharse = new Inventory();
        final String productName = productById.getName();
//        In this case stock is empty.
        if (inventoryList.isEmpty()) {
            Inventory inventoryNew = new Inventory(
                    1L, LocalDateTime.now(),
                    label,
                    productName,
                    true,
                    quantity,
                    price,
                    amount,
                    quantity,
                    newPrice,
                    newAmount,
                    day,
                    month,
                    year
            );
            //SOME OPERATION (CMUP) Cout Moyen Unitaire Pondere.
//            We return this object inventoryToPucharse.
            inventoryToPucharse = inventoryNew;
        }
        int qty = 0;
        double amt = 0;
        for (Inventory inventoryToUpdate : inventoryList) {
            if (inventoryToUpdate.isUp()
                    && productName.equalsIgnoreCase(inventoryToUpdate.getProductName())) {
                final double newPriceToAdd = purchase.getSalePrice();
//                orld quantity + new purchase quantity.
                qty = inventoryToUpdate.getNewQuantity() + quantity; //calcul de la nouvel quatite.
                // calcul du nouveau montant.
                amt = Math.multiplyExact(quantity, (int) newPriceToAdd);

                Inventory inventoryToAdd = new Inventory();
//              Firstly set orld values.
                inventoryToAdd.setLabel(label);
                inventoryToAdd.setProductName(productName);
                inventoryToAdd.setDate(LocalDateTime.now());
                inventoryToAdd.setDay(day);
                inventoryToAdd.setMonth(month);
                inventoryToAdd.setYear(year);

                inventoryToAdd.setOrldQuantity(quantity);
                inventoryToAdd.setOrldPrice(price);
                inventoryToAdd.setOldAmount(amount);

                inventoryToAdd.setNewQuantity(qty);
                inventoryToAdd.setNewPrice(newPrice);
                inventoryToAdd.setNewAmount(amt);
                inventoryToAdd.setUp(true);
                inventoryToPucharse = inventoryToAdd;

                inventoryToUpdate.setUp(false);
/**             Depricated */
                productById.setPrice(newPrice);
                productService.updateProduct(productById);

            } else {
                Inventory inventoryNew = new Inventory(
                        null, LocalDateTime.now(), label, productName, true,
                        quantity,
                        price,
                        amount,
                        quantity,
                        newPrice,
                        newAmount,
                        day,
                        month,
                        year
                );
                inventoryToPucharse = inventoryNew;
                productById.setPrice(newPrice);
                productService.updateProduct(productById);
            }
        }
        return inventoryToPucharse;
    }

    @Override
    public List<Inventory> cmupForSale(String label, List<Sale> sales, List<Inventory> inventoryList) {
        int day = LocalDate.now().getDayOfMonth();
        Month month = LocalDate.now().getMonth();
        Year year = Year.now();
        List<Inventory> inventoryToCMUSale = new ArrayList<>();
//      Default values.
        final LocalDate date = now();
//      Last calculed quantity in our stock.
        int newQuantity = 0;
//      Get last pucharse price.
        double newPrice = 0;
        double oldPrice = 0;
        double newAmount = 0;
        double oldAmount = 0;
//      SOME OPERATION (CMUP) Cout Moyen Unitaire Pondere.
        int quantity = 0;
        double amount = 0;
//      Quantity provider by user.
        int quantityProvideByUser = 0;
        Long productId = 0L;
        String productName = "";

//      Ensure that with have alredy purchase.
        if (inventoryList.isEmpty()) {
            throw new IllegalStateException("We can't perform this action. First purchase this product");
        }

//      Set Sale properties.
        for (Sale sale : sales) {
//            Object
            Inventory inventoryToAdd = new Inventory();
            quantityProvideByUser = sale.getQuantity();

            productName = sale.getProduct().getName();
            for (Inventory stockInventoty : inventoryList) {
//            stock product.
                if (stockInventoty.getNewQuantity() < quantityProvideByUser && stockInventoty.isUp()) {
                    throw new IllegalStateException("Quantity in store it's less thant provider");
                }
                if (productName.equalsIgnoreCase(stockInventoty.getProductName()) && stockInventoty.isUp()) {
//                  We manage only product that is up.
                    oldPrice = stockInventoty.getOrldPrice();
                    newPrice = stockInventoty.getNewPrice();//ancien prix calcule apres achat.
                    oldAmount = quantityProvideByUser * newPrice; // prix calcule
                    newQuantity = stockInventoty.getNewQuantity();
                    newAmount = quantityProvideByUser * newPrice;
                    quantity = newQuantity - quantityProvideByUser;
                    inventoryToAdd.setLabel(label);
                    inventoryToAdd.setOrldQuantity(quantityProvideByUser);
                    inventoryToAdd.setOrldPrice(newPrice);// ancien nouveau prix calcule apre achat.
                    inventoryToAdd.setOldAmount(oldAmount);

                    inventoryToAdd.setNewQuantity(quantity);
                    inventoryToAdd.setNewPrice(newPrice);
                    inventoryToAdd.setNewAmount(newAmount);
                    inventoryToAdd.setProductName(productName);
                    inventoryToAdd.setDate(LocalDateTime.now());
                    inventoryToAdd.setYear(year);
                    inventoryToAdd.setMonth(month);
                    inventoryToAdd.setDay(day);

                    inventoryToAdd.setUp(true);
//                  Turn the position of last product to false
                    stockInventoty.setUp(false);
//                    Update change of inventory after seller.
//                    Save the change mind save the new quantity that reduice
                    inventoryService.updateInventory(stockInventoty);
                    break;
                }
            }
//            Add to list CMUP
            inventoryToCMUSale.add(inventoryToAdd);
        }
        return inventoryToCMUSale;

    }
}