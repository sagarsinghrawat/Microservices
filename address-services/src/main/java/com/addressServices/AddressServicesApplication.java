package com.addressServices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class AddressServicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(AddressServicesApplication.class, args);
	}

}
