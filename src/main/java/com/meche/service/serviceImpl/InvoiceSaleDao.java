package com.meche.service.serviceImpl;

import com.meche.model.InvoiceSale;
import org.springframework.data.domain.Sort;

import java.time.Month;
import java.time.Year;
import java.util.List;

/**
 * @Author sidof
 * @Since 30/10/2023
 * @Version v1.0
 * @YouTube @sidof8065
 */
public interface InvoiceSaleDao {
    List<InvoiceSale> getInvoiceSales();

    List<InvoiceSale> getInvoicesSaleBySaleId(Long saleId);

    InvoiceSale getInvoicesSaleByCustormeId(Long custormeId);

    List<InvoiceSale> getInvoicesSaleByCustormeIds(Long custormeId);

    List<InvoiceSale> addInvoiceSale(List<InvoiceSale> invoiceSales);
//    UPDATE



    List<InvoiceSale> findByInvoiceNumber(String invoiceNumber);

    List<InvoiceSale> findByMonthAndYear(Month month, Year year, Sort sort);

    List<InvoiceSale> findByDayAndMonth(int day, Month month, Sort sort);

}
