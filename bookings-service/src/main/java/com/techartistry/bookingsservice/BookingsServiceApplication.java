package com.techartistry.bookingsservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients //se indica que se usara feign
@EnableDiscoveryClient //se indica que es un cliente de eureka
@SpringBootApplication
public class BookingsServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(BookingsServiceApplication.class, args);
    }
}
