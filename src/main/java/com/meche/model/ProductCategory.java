package com.meche.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.GenerationType.SEQUENCE;

/**
 * @Author sidof
 * @Since 28/11/2023
 * @Version v1.0
 * @YouTube @sidof8065
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ProductCategory {
    @Id
    @SequenceGenerator(name = "sequence_id_employee",allocationSize = 1,sequenceName = "sequence_id_employee") @GeneratedValue(strategy = SEQUENCE,generator = "sequence_id_employee")
    private Long id;
    private String name;
    @JsonIgnore
    @OneToMany(mappedBy = "productCategory")
    private List<Product> product = new ArrayList<>();
//    @ManyToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "category_type_id",nullable = true,referencedColumnName = "id")
//    private CategoryType categoryType;
}
