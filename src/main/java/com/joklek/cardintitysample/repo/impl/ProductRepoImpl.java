package com.joklek.cardintitysample.repo.impl;

import com.joklek.cardintitysample.repo.ProductRepo;
import com.joklek.cardintitysample.shop.Product;
import com.joklek.cardintitysample.shop.impl.ProductImpl;
import org.apache.commons.lang3.RandomStringUtils;

import java.math.BigDecimal;
import java.util.*;

public class ProductRepoImpl implements ProductRepo {

    private Map<UUID, Product> products;

    public ProductRepoImpl() {
        products = new HashMap<>();
        Random rand = new Random();
        for(int i = 0; i < 20; i++) {
            UUID newId= UUID.randomUUID();
            String title = RandomStringUtils.random(10, true, false);
            products.put(newId, new ProductImpl(newId, title, BigDecimal.valueOf(rand.nextFloat())));
        }
    }

    @Override
    public Product getProductById(UUID id) {
        return products.get(id);
    }

    @Override
    public List<Product> getProducts() {
        return new ArrayList<>(products.values());
    }

    @Override
    public void removeProductById(UUID id) {
        products.remove(id);
    }
}
