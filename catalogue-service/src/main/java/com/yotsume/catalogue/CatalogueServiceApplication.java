package com.yotsume.catalogue;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class CatalogueServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CatalogueServiceApplication.class, args);
	}

}
