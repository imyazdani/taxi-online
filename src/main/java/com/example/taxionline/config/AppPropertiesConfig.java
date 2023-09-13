package com.example.taxionline.config;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;


@Getter
@ConfigurationProperties(prefix = "app")
@ConstructorBinding
public class AppPropertiesConfig {

    private final Security security;
    private final Trip trip;

    public AppPropertiesConfig(Security security, Trip trip) {
        this.security = security;
        this.trip = trip;
    }

    @Getter
    @ConstructorBinding
    public static class Security{
        private final String passengerRegister;
        private final String driverRegister;

        public Security(String passengerRegister, String driverRegister) {
            this.passengerRegister = passengerRegister;
            this.driverRegister = driverRegister;
        }
    }

    @Getter
    @ConstructorBinding
    public static class Trip{
        private final Integer distance;
        private final Integer limit;

        public Trip(Integer distance, Integer limit) {
            this.distance = distance;
            this.limit = limit;
        }
    }
}
