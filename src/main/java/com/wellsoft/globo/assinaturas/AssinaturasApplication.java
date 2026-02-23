package com.wellsoft.globo.assinaturas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(basePackages = {"com.wellsoft.globo.assinaturas"})
@SpringBootApplication
public class AssinaturasApplication {

	public static void main(String[] args) {
		SpringApplication.run(AssinaturasApplication.class, args);
	}

}
