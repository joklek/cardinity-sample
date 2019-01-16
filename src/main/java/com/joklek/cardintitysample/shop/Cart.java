package com.joklek.cardintitysample.shop;

import org.springframework.lang.NonNull;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Interface that control the shopping cart
 */
public interface Cart {

    /**
     * Gets unique id of cart
     * @return id of cart
     */
    @NonNull
    UUID getId();

    /**
     * Gets a list of all products held in cart
     * @return {@link List} of {@link Product}s held in cart
     */
    @NonNull
    Map<Product, Integer> getProductsAndAmounts();

    /**
     * Adds a product to the cart by object
     * @param product object
     */
    void addProduct(@NonNull Product product);

    /**
     * Adds a product to the cart by id
     * @param id product id
     */
    void addProduct(@NonNull UUID id);

    /**
     * Removes a product from the cart by object
     * @param product object
     */
    void removeProduct(@NonNull Product product);

    /**
     * Removes a product from the cart by id
     * @param id product id
     */
    void removeProduct(@NonNull UUID id);

    /**
     * Removes all products from cart
     */
    void clearCart();

    /**
     * Gets total items. For example you can have 3 items of one type. In this case, you will get 3
     * @return returns total sum of items
     */
    int getTotalItems();

    /**
     * Calculates and returns total price of cart
     * @return total price of cart
     */
    @NonNull
    BigDecimal getTotalPrice();
}
