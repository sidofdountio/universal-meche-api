package com.meche.repo;

import com.meche.model.InvoiceSale;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.Month;
import java.time.Year;
import java.util.List;

/**
 * @Author sidof
 * @Since 30/10/2023
 * @Version v1.0
 * @YouTube @sidof8065
 */
public interface InvoiceSaleRepo extends JpaRepository<InvoiceSale, Long> {
    @Override
    @Query("SELECT i FROM InvoiceSale i  ORDER BY id ")
    List<InvoiceSale> findAll();
    List<InvoiceSale> findBySaleId(Long saleId);

    //    @Query("select distinct invoiceNumber,createAt,total from invoiceSale")
    @Query(value = "SELECT DISTINCT i.invoice_number,i.id, i.status, i.sub_total, i.tax, i.total, i.create_at, i.day, i.month, i.year,i.customer_id,i.sale_id FROM invoice_sale i, customer c, sale s where s.id = i.sale_id and c.id = i.customer_id", nativeQuery = true)
    List<InvoiceSale> findDistinctInvoiceNumber();
    @Query("SELECT i FROM InvoiceSale i WHERE i.invoiceNumber = :invoiceNumber")
    List<InvoiceSale> findByInvoiceNumber(@Param("invoiceNumber")String invoiceNumber);
    @Query("SELECT i FROM InvoiceSale i WHERE i.month = :month AND i.year = :year ")
    List<InvoiceSale> findByMonthAndYear(@Param("month") Month month,@Param("year") Year year,Sort sort);
    @Query("SELECT i FROM InvoiceSale i WHERE i.day = :day AND i.month = :month ")
    List<InvoiceSale> findByDayAndMonth(@Param("day") int day,@Param("month") Month month,Sort sort);

    List<InvoiceSale> findAllByCustomerId(Long customerId);

}
