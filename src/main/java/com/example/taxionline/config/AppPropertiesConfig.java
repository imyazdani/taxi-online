package com.example.taxionline.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("app")
public class AppPropertiesConfig {

    private final Security security;
    private final Trip trip;

    @Data
    public static class Security{
        private final String passengerRegister;
        private final String driverRegister;
    }

    @Data
    public static class Trip{
        private final Integer distance;
        private final Integer limit;
    }
}
