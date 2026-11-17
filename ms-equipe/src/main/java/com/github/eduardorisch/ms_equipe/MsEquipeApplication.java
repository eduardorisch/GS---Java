package com.github.eduardorisch.ms_equipe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class MsEquipeApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsEquipeApplication.class, args);
	}

}
