package com.meche.api;

import com.meche.api.operation.InventoryOperation;
import com.meche.api.operation.Utils;
import com.meche.model.Inventory;
import com.meche.model.InvoiceSale;
import com.meche.model.Sale;
import com.meche.model.Transaction;
import com.meche.service.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.meche.model.enume.Status.PAID;
import static com.meche.model.enume.Status.PENDING;
import static com.meche.model.enume.TransactionType.PURCHASE;
import static com.meche.model.enume.TransactionType.SALE;
import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.*;

/**
 * @Author sidof
 * @Since 20/05/2023
 * @Version v1.0
 */
@RestController
@RequestMapping("/api/v1/hair/sale")
@CrossOrigin(origins = "http://localhost:4200/", maxAge = 3600)
@RequiredArgsConstructor
@Transactional
public class SaleApi {
    private final SaleService saleService;
    private final InventoryService inventoryService;
    private final ProductService productService;
    private final InventoryOperation inventoryOperation;
    private final InvoiceSaleService invoiceSaleService;
    private final TransactionService transactionService;


    @GetMapping
    public ResponseEntity<List<Sale>> getSales() {
        final List<Sale> sales = saleService.SALES();
        return new ResponseEntity<List<Sale>>(sales, OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sale> getSale(@PathVariable("id") Long id) throws InterruptedException {
        Sale sale = saleService.getSale(id);
        TimeUnit.SECONDS.sleep(1);
        return ResponseEntity.ok(sale);
    }


    /**
     * By Month and Year parameter
     */
    @GetMapping("/month/{month}/{year}")
    public ResponseEntity<List<Sale>> getInvoiceByMonthAndYear(
            @PathVariable("month") Month month, @PathVariable("year")Year year)
            throws InterruptedException {
        List<Sale> byMonthAndYear = saleService.findByMonthAndYear(month, year, Sort.by("month", "year"));
        TimeUnit.SECONDS.sleep(1);
        return new ResponseEntity<List<Sale>>(byMonthAndYear, OK);
    }

    /**
     * By Day and Month parameters.
     */
    @GetMapping("/day/{day}/{month}")
    public ResponseEntity<List<Sale>> getInvoiceByDayAndMonth(
            @PathVariable("day")int day,@PathVariable ("month") Month month)
            throws InterruptedException {
        List<Sale> byDayAndMonth = saleService.findByDayAndMonth(day, month, Sort.by("day", "month"));
        TimeUnit.SECONDS.sleep(1);
        return new ResponseEntity<List<Sale>>(byDayAndMonth, OK);
    }

    @PutMapping("/validSale")
    public ResponseEntity<Sale> ValidSale(@RequestBody Sale saleToValid)
            throws InterruptedException {
        Sale saleById = saleService.getSale(saleToValid.getId());
        if (saleById == null){
            return new ResponseEntity<>(NOT_FOUND);
        }
        saleToValid.setStatus(PAID);
        Sale validSale = saleService.updateSale(saleToValid);
        return new ResponseEntity<>(validSale,CREATED);
    }

    @PostMapping("/addSale")
    public ResponseEntity<List<Sale>> addSale(@RequestBody List<Sale> saleToSave) throws InterruptedException {
        double total = 0;

        List<InvoiceSale> invoiceSales = new ArrayList<>();
        final List<Inventory> inventoryList = inventoryService.INVENTORIES();
        final List<Inventory> cmupForSale = inventoryOperation.cmupForSale("SALE", saleToSave, inventoryList);

        if (cmupForSale == null) {
            throw new NullPointerException("error");
        }

        for ( Sale sale: saleToSave) {
            total += sale.getAmount();
        }
        var transaction = Transaction.builder()
                .amount(total)
                .id(null)
                .timestamp(LocalDateTime.now())
                .type(SALE)
                .sender(saleToSave.get(1).getCustomer().getName())
                .receiver("UNIVERSAL MECHE")
                .trsansactionId(Utils.generateTransactionId())
                .build();
        Transaction transactionSaved = transactionService.save(transaction);
        for (Sale sale : saleToSave) {
            sale.setCreateAt(now());
            sale.setStatus(PENDING);
            sale.setDay(new Date().getDay());
            sale.setMonth(LocalDate.now().getMonth());
            sale.setYear(Year.now());
            sale.setTransaction(transactionSaved);
        }
//        saved inventory.
        inventoryService.addInventoryForSale(cmupForSale);
//        saved sale
        final List<Sale> saleSaved = saleService.addSale(saleToSave);

        String invoiceNumber = Utils.generateInvoiceNumber();
//  Set invoice sale properties.
        for (Sale sale : saleSaved) {
            total += sale.getAmount();
            var invoiceSale = InvoiceSale.builder()
                    .sale(sale)
                    .customer(sale.getCustomer())
                    .tax(0)
                    .subTotal(total)
                    .total(total)
                    .invoiceNumber(invoiceNumber)
                    .status(PENDING)
                    .createAt(LocalDate.now())
                    .day(LocalDate.now().getDayOfMonth())
                    .month(LocalDate.now().getMonth())
                    .year(Year.now())
                    .build();
            invoiceSales.add(invoiceSale);
        }
//        saved invoice
        invoiceSaleService.addInvoiceSale(invoiceSales);
        TimeUnit.SECONDS.sleep(1);
        return new ResponseEntity<>(saleSaved, CREATED);
    }
}