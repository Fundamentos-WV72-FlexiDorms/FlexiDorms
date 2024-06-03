package com.techartistry.roomsservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients //se indica que se usara feign
@EnableDiscoveryClient //se indica que es un cliente de eureka
@SpringBootApplication
public class RoomsServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(RoomsServiceApplication.class, args);
	}
}
