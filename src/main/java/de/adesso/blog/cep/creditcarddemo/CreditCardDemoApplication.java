package de.adesso.blog.cep.creditcarddemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CreditCardDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(CreditCardDemoApplication.class, args);
	}
}
