package com.meche;

import com.meche.model.*;
import com.meche.repo.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {

        SpringApplication.run(Main.class, args);
    }

    @Bean
    CommandLineRunner runner(
            CustomerRepo customerRepo,
            ProductCategoryRepo productCategoryRepo,
            ProductRepo productRepo,
            SupplierRepo supplierRepo) {
        return args -> {


            customerRepo.save(new Customer(
                    null,
                    "John",
                    "unknow",
                    "unknow",
                    "unknow",
                    new ArrayList<>(),
                    new ArrayList<>()));

            ProductCategory productCategory = productCategoryRepo.save(new ProductCategory(
                    1L,
                    "Meche",
                    new ArrayList<>()
                    ));
            ProductCategory productCategory2 = productCategoryRepo.save(new ProductCategory(
                    2L,
                    "Savon",
                    new ArrayList<Product>()
                    ));

            productRepo.save(new Product(1L,
                    "Star Africa",
                    "noir",
                    0,
                    1500,
                    "Description",
                    900,
                    "codeSA",
                    new ArrayList<Purchase>(),
                   null,
                    null,
                    productCategory,
                    ""));
            productRepo.save(new Product(2L,
                    "Jazz",
                    "",
                    0,
                    500,
                    "",
                    250,
                    "codeJa",
                    new ArrayList<Purchase>(),
                    null,
                    null,
                    productCategory2,
                    ""));

            supplierRepo.save(new Supplier(null, "Max", "max@gmail.com", "67828929", "Yaounde", new ArrayList<>()));
        };
    }

}
