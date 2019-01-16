package com.joklek.cardintitysample.config;

import com.joklek.cardintitysample.ShopDriver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
public class DefaultShopConfiguration {
    @Bean
    public ShopDriver driver() {
        return new ShopDriver();
    }
}
