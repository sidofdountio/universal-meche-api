package com.meche;

import com.meche.model.AnotherCharge;
import com.meche.model.Charge;
import com.meche.repo.AnotherChargeRepo;
import com.meche.repo.ChargeRepo;
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
	CommandLineRunner runner(AnotherChargeRepo anotherChargeRepo,
							 ChargeRepo chargeRepo){
		return args -> {
			AnotherCharge anotherCharge = anotherChargeRepo.save(new AnotherCharge(null, "", 10, new ArrayList<>()));
			System.out.println(anotherCharge);
			var charge = Charge.builder()
					.anotherCharge(anotherCharge)
					.impot(10)
					.loyer(12)
					.electricity(4)
					.totalSalary(23)
					.transaport(25)
					.ration(23)
					.build();
			chargeRepo.save(charge);
		};
	}

}
