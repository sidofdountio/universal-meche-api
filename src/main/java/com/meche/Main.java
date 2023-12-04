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
    CommandLineRunner runner(AnotherChargeRepo anotherChargeRepo,
                             ChargeRepo chargeRepo,
                             CustomerRepo customerRepo,
                             ProductCategoryRepo productCategoryRepo,
                             ProductRepo productRepo,
                             SupplierRepo supplierRepo) {
        return args -> {
            AnotherCharge anotherCharge = anotherChargeRepo.save(new AnotherCharge(null, "", 10, new ArrayList<>()));

            var charge = Charge.builder()
                    .anotherCharge(anotherCharge)
                    .impot(10)
                    .loyer(12)
                    .electricity(4)
                    .totalSalary(23)
                    .transport(25)
                    .ration(23)
                    .build();
            chargeRepo.save(charge);

            customerRepo.save(new Customer(
                    null,
                    "unknow",
                    "unknow",
                    "unknow",
                    "unknow",
                    new ArrayList<>(),
                    new ArrayList<>()));
            ProductCategory productCategory = productCategoryRepo.save(new ProductCategory(null, "Naturelle", new ArrayList<>(), null));
            productRepo.save(new Product(null,"Star Africa","noir",2,0,"Description",100,"code",new ArrayList<>(),null,null,productCategory));

            supplierRepo.save(new Supplier(null,"Max","max@gmail.com","67828929","Yaounde",new ArrayList<>()));
        };
    }

}
