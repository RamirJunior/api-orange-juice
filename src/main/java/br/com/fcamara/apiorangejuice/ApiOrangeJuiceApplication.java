package br.com.fcamara.apiorangejuice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

@EnableCaching
@SpringBootApplication
public class ApiOrangeJuiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiOrangeJuiceApplication.class, args);
	}
}
