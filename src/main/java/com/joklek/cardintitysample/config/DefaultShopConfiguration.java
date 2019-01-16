package com.joklek.cardintitysample.config;

import com.cardinity.CardinityClient;
import com.joklek.cardintitysample.ShopDriver;
import com.joklek.cardintitysample.repo.ProductRepo;
import com.joklek.cardintitysample.repo.impl.ProductRepoImpl;
import com.joklek.cardintitysample.window.CartWindow;
import com.joklek.cardintitysample.window.PayWindow;
import com.joklek.cardintitysample.window.ProcessingWindow;
import com.joklek.cardintitysample.window.ProductWindow;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
public class DefaultShopConfiguration {
    @Bean
    public CardinityClient client(@Value("${cardinity.consumer_key}") String key, @Value("${cardinity.consumer_secret}") String secret) {
        return new CardinityClient(key, secret);
    }

    @Bean
    public ShopDriver driver() {
        return new ShopDriver();
    }

    @Bean
    public ProductRepo productRepo() {
        return new ProductRepoImpl();
    }

    @Bean
    public ProductWindow productWindow() {
        return new ProductWindow();
    }

    @Bean
    public CartWindow cartWindow() {
        return new CartWindow();
    }

    @Bean
    public PayWindow payWindow() {
        return new PayWindow();
    }

    @Bean
    public ProcessingWindow processingWindow() {
        return new ProcessingWindow();
    }
}
