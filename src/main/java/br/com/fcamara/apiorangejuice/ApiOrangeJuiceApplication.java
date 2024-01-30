package br.com.fcamara.apiorangejuice;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class ApiOrangeJuiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiOrangeJuiceApplication.class, args);
	}

}
