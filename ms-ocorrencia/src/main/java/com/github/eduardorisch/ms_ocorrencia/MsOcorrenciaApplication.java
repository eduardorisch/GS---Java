package com.github.eduardorisch.ms_ocorrencia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MsOcorrenciaApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsOcorrenciaApplication.class, args);
	}

}
