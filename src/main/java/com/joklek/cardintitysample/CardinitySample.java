package com.joklek.cardintitysample;

import com.joklek.cardintitysample.config.DefaultShopConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class CardinitySample {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DefaultShopConfiguration.class);
        ctx.getBean(ShopDriver.class);
        ctx.close();
    }
}
