package com.marhaba.notes_app.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI notesOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Notes API")
                        .description("API documentation for the Notes application")
                        .version("1.0"));
    }
}
