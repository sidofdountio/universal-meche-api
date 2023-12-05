package com.meche.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.meche.model.enume.TransactionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
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
public class Transaction {
    @Id
    @SequenceGenerator(name = "sequence_id_transaction", allocationSize = 1, sequenceName = "sequence_id_transaction")
    @GeneratedValue(strategy = SEQUENCE, generator = "sequence_id_transaction")
    private Long id;
    private String trsansactionId;
    private double amount;
    private String sender;
    private String receiver;
    private LocalDateTime timestamp;
    private TransactionType type;
    @JsonIgnore
    @OneToMany(mappedBy = "transaction")
    private List<Sale> sales= new ArrayList<>();
    @JsonIgnore
    @OneToMany(mappedBy = "transaction")
    private List<Purchase> purchases= new ArrayList<>();

}
