package com.joklek.cardintitysample.shop.impl;

import com.joklek.cardintitysample.repo.ProductRepo;
import com.joklek.cardintitysample.shop.Cart;
import com.joklek.cardintitysample.shop.Product;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.*;

public class CartImpl implements Cart {

    @Autowired
    private ProductRepo repository;

    private final Map<Product, Integer> productsAndCounts;
    private final UUID id;

    public CartImpl(UUID id) {
        this.id = id;
        productsAndCounts = new HashMap<>();
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public Map<Product, Integer> getProductsAndAmounts() {
        return productsAndCounts;
    }

    @Override
    public void addProduct(Product product) {
        if(productsAndCounts.containsKey(product)) {
            Integer oldCount = productsAndCounts.get(product);
            productsAndCounts.put(product, oldCount + 1);
        }
        else {
            productsAndCounts.put(product, 1);
        }
    }

    @Override
    public void addProduct(UUID id) {
        addProduct(repository.getProductById(id));
    }

    @Override
    public void removeProduct(Product product) {
        removeProduct(product.getId());
    }

    @Override
    public void removeProduct(UUID id) {
        productsAndCounts.remove(repository.getProductById(id));
    }

    @Override
    public void clearCart() {
        productsAndCounts.clear();
    }

    @Override
    public int getTotalItems() {
        return productsAndCounts.values().stream()
                .mapToInt(x -> x)
                .sum();
    }

    @Override
    public BigDecimal getTotalPrice() {
        return productsAndCounts.entrySet().stream()
                .map(entry -> {
                    Integer productCount = entry.getValue();
                    BigDecimal priceOfSingleProduct = entry.getKey().getPrice();
                    if(productCount > 1) {
                        BigDecimal bigDecimalProductCount = BigDecimal.valueOf(productCount);
                        priceOfSingleProduct = priceOfSingleProduct.multiply(bigDecimalProductCount);
                    }
                    return priceOfSingleProduct;
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
