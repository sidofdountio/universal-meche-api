package com.meche.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
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
public class CategoryType {
    @Id
    @SequenceGenerator(name = "sequence_id_category_type",
            allocationSize = 1,
            sequenceName = "sequence_id_category_type")
    @GeneratedValue(strategy = SEQUENCE,
            generator = "sequence_id_category_type")
    private Long id;
    private String name;
    @JsonIgnore
    @OneToMany(mappedBy = "categoryType")
    private List<ProductCategory>productCategories=new ArrayList<>();
}
