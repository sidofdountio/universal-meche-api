package com.meche.model;

import com.meche.model.enume.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.GenerationType.SEQUENCE;

/**
 * @Author sidof
 * @Since 27/11/2023
 * @Version v1.0
 * @YouTube @sidof8065
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Sale {
    @Id
    @SequenceGenerator(name = "sequence_id_sale", allocationSize = 1, sequenceName = "sequence_id_sale")
    @GeneratedValue(strategy = SEQUENCE, generator = "sequence_id_sale")
    private Long id;
    private int quantity;
    private double amount;
    private double price;
    private Status status;
    private LocalDateTime createAt;
    private int day;
    private Month month;
    private Year year;
    private String paymentType;
    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id",foreignKey = @ForeignKey(name = "customer_sale"))
    private Customer customer;
    @ManyToOne()
    @JoinColumn(name = "product_id", referencedColumnName = "id",foreignKey = @ForeignKey(name = "product_sale"))
    private Product product;
    @ManyToOne()
    @JoinColumn(name = "transaction_id",referencedColumnName = "id",foreignKey =@ForeignKey(name = "transaction_sale") )
    private Transaction transaction;
    @OneToMany
    private List<InvoiceSale> invoiceSales = new ArrayList<>();

}