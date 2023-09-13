package com.example.taxionline.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecurityScheme(
        type = SecuritySchemeType.HTTP,
        name = "basicAuth",
        scheme = "basic")
public class OpenApiDocConfig {

    @Bean
    public OpenAPI customizeOpenApiTitle() {

        Info info = new Info()
                .title("Taxi Online API")
                .version("1.0")
                .description("This API exposes endpoints to a taxi online system.");

        return new OpenAPI().info(info);
    }
}
