package br.com.project;

import br.com.project.config.EnvConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "br.com.project")
public class FluxyApplication {

	public static void main(String[] args) {

		System.out.println("Banco de Dados: " + EnvConfig.getDbUrl());
		SpringApplication.run(FluxyApplication.class, args);
	}

}
	