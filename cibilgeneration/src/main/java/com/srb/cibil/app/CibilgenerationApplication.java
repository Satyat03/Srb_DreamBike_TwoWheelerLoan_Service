package com.srb.cibil.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class CibilgenerationApplication {

	public static void main(String[] args) {
		SpringApplication.run(CibilgenerationApplication.class, args);
	}

}
