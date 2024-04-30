package com.techartistry.accountservice;

import com.techartistry.accountservice.user.domain.enums.ERole;
import com.techartistry.accountservice.user.infrastructure.repositories.IRoleRepository;
import com.techartistry.accountservice.shared.util.Utilities;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

@Slf4j
@EnableDiscoveryClient //se indica que es un cliente de eureka
@SpringBootApplication
public class AccountServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(AccountServiceApplication.class, args);
	}

	/**
	 * Bean que se encarga de insertar los roles por defecto
	 * @param roleRepository Repositorio de roles
	 */
	@Bean
	CommandLineRunner initDatabase(IRoleRepository roleRepository) {
		return args -> {
			Utilities.insertRoleIfNotFound(roleRepository, ERole.ROLE_USER);
			Utilities.insertRoleIfNotFound(roleRepository, ERole.ROLE_ADMIN);
		};
	}
}
