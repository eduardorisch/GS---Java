package com.github.eduardorisch.ms_equipe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MsEquipeApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsEquipeApplication.class, args);
	}

}
