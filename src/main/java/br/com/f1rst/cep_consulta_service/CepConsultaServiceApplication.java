package br.com.f1rst.cep_consulta_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class CepConsultaServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CepConsultaServiceApplication.class, args);
	}

}
