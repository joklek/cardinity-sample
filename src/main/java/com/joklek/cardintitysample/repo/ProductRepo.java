package com.joklek.cardintitysample.repo;

import com.joklek.cardintitysample.shop.Product;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.UUID;

/**
 * Product repository
 */
public interface ProductRepo {
    @NonNull
    Product getProductById(@NonNull UUID id);

    @NonNull
    List<Product> getProducts();

    @NonNull
    void removeProductById(@NonNull UUID id);
}
