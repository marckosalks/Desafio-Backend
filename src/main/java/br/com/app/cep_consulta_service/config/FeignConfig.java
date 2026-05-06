package br.com.app.cep_consulta_service.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "br.com.app.cep_consulta_service.client")
public class FeignConfig {
}
