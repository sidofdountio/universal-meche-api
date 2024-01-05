package com.meche;

import com.meche.model.*;
import com.meche.repo.*;
import com.meche.security.model.RegisterRequest;
import com.meche.security.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;

@SpringBootApplication
public class Main {

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}

	@Bean
	CommandLineRunner runner(
							 ChargeRepo chargeRepo,
							 CustomerRepo customerRepo,
							 ProductCategoryRepo productCategoryRepo,
							 ProductRepo productRepo,
							 SupplierRepo supplierRepo,
							 UserService userService) {
		return args -> {
			userService.registerAdmin(new RegisterRequest("Nguesson",
					"universalmeche@gmail.com","universal1"));

			customerRepo.save(new Customer(
					null,
					"unknow",
					"unknow",
					"unknow",
					"unknow",
					new ArrayList<>(),
					new ArrayList<>()));
			ProductCategory productCategory = productCategoryRepo.save(new ProductCategory(null, "Naturelle", new ArrayList<>()));
			productRepo.save(new Product(null,"Star Africa","","",0,"Description",100,"code",new ArrayList<>(),null,null,productCategory));

			supplierRepo.save(new Supplier(null,"Fournisseur","max@gmail.com","67828929","Yaounde",new ArrayList<>()));
		};
	}


}
