package com.example.example.configuration;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SpringDocConfiguration {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("NLP service")
                                .description("Сервис для работы с разными ML моделями для обработки текста.")
                                .contact(new Contact()
                                        .name("Alexander Chapchuk")
                                        .email("gasfull98@gmail.com")
                                )
                                .license(new License()
                                        .name("MIT")
                                        .url("https://mit-license.org/")
                                )
                                .version("1.0.0")
                )
                .servers(
                        List.of(
                                new Server()
                                        .url("https://back-hack.bigtows.org/ml")
                                        .description("Production"),
                                new Server()
                                        .url("http://127.0.0.1:8082")
                                        .description("Dev")
                        )
                );
    }
}
