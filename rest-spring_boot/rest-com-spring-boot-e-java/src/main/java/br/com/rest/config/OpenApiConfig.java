package br.com.rest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class OpenApiConfig {

	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI()
				.info(new Info()
						.title("RESTful com Java")
						.version("v1")
						.description("API de pessoas")
						.termsOfService("htpps://br.rest.com/cursos")
						.license(
								new License()
								.name("Apache2.0")
								.url("https://br.rest.com/cursos")));
		
	}
}
