package br.com.fcamara.apiorangejuice.configs;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocOpenApiConfig {

    @Bean
    public OpenAPI openAPI(){
        return new OpenAPI()
                .components(new Components().addSecuritySchemes("security", securityScheme()))
                .info(
                        new Info()
                                .title("Orange Juice API")
                                .description("API for registering and displaying developed projects")
                                .version("v1")
                );
    }

    private SecurityScheme securityScheme() {
        return new SecurityScheme()
                .description("Insert a valid bearer token to proceed")
                .type(SecurityScheme.Type.HTTP)
                .in(SecurityScheme.In.HEADER)
                .scheme("bearer")
                .bearerFormat("JWT")
                .name("security");
    }
}