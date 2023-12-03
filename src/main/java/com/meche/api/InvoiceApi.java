package com.meche.api;

import com.meche.model.InvoiceSale;
import com.meche.service.InvoiceSaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Month;
import java.time.Year;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.springframework.http.HttpStatus.OK;

/**
 * @Author sidof
 * @Since 31/10/2023
 * @Version v1.0
 * @YouTube @sidof8065
 */
@RestController
@RequestMapping("/api/v1/hair/invoice")
@CrossOrigin(origins = "http://localhost:4200/", maxAge = 3600)
@RequiredArgsConstructor
public class InvoiceApi {
    private final InvoiceSaleService invoiceSaleService;

    /**
     * By invoice number without passed parameter
     */
    @GetMapping
    public ResponseEntity<List<InvoiceSale>> getInvoiceNumber() throws InterruptedException {
        List<InvoiceSale> byInvoiceNumber = invoiceSaleService.findByInvoiceNumber();
        TimeUnit.SECONDS.sleep(2);
        return new ResponseEntity<List<InvoiceSale>>(byInvoiceNumber, OK);
    }

    /**
     * By invoice number.
     */
    @GetMapping("/{invoiceNumber}")
    public ResponseEntity<List<InvoiceSale>> getInvoiceNumber(@PathVariable("invoiceNumber") String invoicenumber)
            throws InterruptedException {
        List<InvoiceSale> byInvoiceNumber = invoiceSaleService.findByInvoiceNumber(invoicenumber);
        TimeUnit.SECONDS.sleep(1);
        return new ResponseEntity<List<InvoiceSale>>(byInvoiceNumber, OK);
    }

    /**
     * By Month and Year parameter
     */
    @GetMapping("/month/{month}/{year}")
    public ResponseEntity<List<InvoiceSale>> getInvoiceByMonthAndYear(
            @PathVariable("month") Month month, @PathVariable("year")Year year)
            throws InterruptedException {
        List<InvoiceSale> byMonthAndYear = invoiceSaleService.findByMonthAndYear(month, year, Sort.by("month", "year"));
        TimeUnit.SECONDS.sleep(1);
        return new ResponseEntity<List<InvoiceSale>>(byMonthAndYear, OK);
    }

    /**
     * By Day and Month parameters.
     */
    @GetMapping("/day/{day}/{month}")
    public ResponseEntity<List<InvoiceSale>> getInvoiceByDayAndMonth(
            @PathVariable("day")int day,@PathVariable ("month") Month month)
            throws InterruptedException {
        List<InvoiceSale> byDayAndMonth = invoiceSaleService.findByDayAndMonth(day, month, Sort.by("day", "month"));
        TimeUnit.SECONDS.sleep(1);
        return new ResponseEntity<List<InvoiceSale>>(byDayAndMonth, OK);
    }



}
