package com.example.taxionline;

import com.example.taxionline.config.AppPropertiesConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(AppPropertiesConfig.class)
public class TaxiOnlineApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaxiOnlineApplication.class, args);
    }

}
