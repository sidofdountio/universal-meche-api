package com.meche.service;

import com.meche.model.InvoiceSale;
import com.meche.repo.InvoiceSaleRepo;
import com.sidof.service.interfaceService.InvoiceSaleDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Month;
import java.time.Year;
import java.util.List;

/**
 * @Author sidof
 * @Since 30/10/2023
 * @Version v1.0
 * @YouTube @sidof8065
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class InvoiceSaleService implements InvoiceSaleDao {
    private final InvoiceSaleRepo invoiceSaleRepo;

    @Override
    public List<InvoiceSale> getInvoiceSales() {
        log.info("Fetching  sale invoice.");
        return invoiceSaleRepo.findAll();
    }

    @Override
    public List<InvoiceSale> addInvoiceSale(List<InvoiceSale> invoiceSale) {
        log.info("saved new invoice sale.");
        return invoiceSaleRepo.saveAll(invoiceSale);
    }

    @Override
    public List<InvoiceSale> getInvoicesSaleBySaleId(Long saleId) {
        List<InvoiceSale> bySaleId = invoiceSaleRepo.findBySaleId(saleId);
        log.info("Fetching  invoice sale saleId : {}.", saleId);
        return bySaleId;
    }


    @Override
    public List<InvoiceSale> getInvoicesSaleByCustormeIds(Long custormeId) {
        log.info("Fetching invoice sale by {}", custormeId);
        return invoiceSaleRepo.findAllByCustomerId(custormeId);
    }

    @Override
    public InvoiceSale getInvoicesSaleByCustormeId(Long custormeId) {
        log.info("Fetching invoice sale by {}", custormeId);
        return null;
    }

    /**
     * NEW aproches to retriving data
     */
    @Override
    public List<InvoiceSale> findByInvoiceNumber() {
        log.info("Fectching Invoice Sale By Invoice number .");
        return invoiceSaleRepo.findDistinctInvoiceNumber();
    }

    @Override
    public List<InvoiceSale> findByInvoiceNumber(String invoicenumber) {
        log.info("Fectching Invoice Sale By Invoice number {} .", invoicenumber);
        return invoiceSaleRepo.findByInvoiceNumber(invoicenumber);
    }

    @Override
    public List<InvoiceSale> findByMonthAndYear(Month month, Year year, Sort sort) {
        log.info("Fectching Invoice Sale By month {} and year {} .", month, year);
        return invoiceSaleRepo.findByMonthAndYear(month, year, sort.ascending());
    }

    @Override
    public List<InvoiceSale> findByDayAndMonth(int day, Month month, Sort sort) {
        log.info("Fectching Invoice Sale By day {} and month {} .", day, month);
        return invoiceSaleRepo.findByDayAndMonth(day, month, sort.ascending());
    }
}
