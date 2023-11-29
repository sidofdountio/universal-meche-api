package com.meche.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.GenerationType.SEQUENCE;

/**
 * @Author sidof
 * @Since 28/11/2023
 * @Version v1.0
 * @YouTube @sidof8065
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity

public class Charge {
    @Id
    @SequenceGenerator(name = "sequence_id_charge",
            allocationSize = 1,
            sequenceName = "sequence_id_charge")
    @GeneratedValue(strategy = SEQUENCE,
            generator = "sequence_id_charge")
    private Long id;
    private double totalSalary;
    private double impot;
    private double  electricity;
    private double  loyer;
    private double  transaport;
    private double  ration;
    @ManyToOne(fetch =  FetchType.EAGER)
    @JoinColumn(name = "another_charge_id",
            referencedColumnName = "id",nullable = true)
    private AnotherCharge anotherCharge;


}
