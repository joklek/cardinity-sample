package com.joklek.cardintitysample;

import com.joklek.cardintitysample.config.DefaultShopConfiguration;
import com.joklek.cardintitysample.window.ProductWindow;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Collections;

public class CardinitySample {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DefaultShopConfiguration.class);
        ctx.getBean(ProductWindow.class).render(Collections.emptyMap());
        ctx.close();
    }
}
