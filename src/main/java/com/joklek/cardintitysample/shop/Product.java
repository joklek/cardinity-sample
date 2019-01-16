package com.joklek.cardintitysample.shop;

import org.springframework.lang.NonNull;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Interface for product
 */
public interface Product {

    @NonNull
    UUID getId();

    /**
     * Gets the title of the product
     * @return product title
     */
    String getTitle();

    /**
     * Gets the price of the product
     * @return product price
     */
    BigDecimal getPrice();
}
