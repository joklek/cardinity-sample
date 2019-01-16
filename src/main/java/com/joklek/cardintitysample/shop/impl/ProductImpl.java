package com.joklek.cardintitysample.shop.impl;

import com.joklek.cardintitysample.shop.Product;

import java.util.UUID;

public class ProductImpl implements Product {

    private final UUID id;
    private final String title;
    private final double price;

    public ProductImpl(UUID id, String title, double price) {
        this.id = id;
        this.title = title;
        this.price = price;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public double getPrice() {
        return price;
    }
}
