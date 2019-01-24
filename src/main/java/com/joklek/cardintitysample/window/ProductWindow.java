package com.joklek.cardintitysample.window;

import com.joklek.cardintitysample.repo.ProductRepo;
import com.joklek.cardintitysample.shop.Cart;
import com.joklek.cardintitysample.shop.Product;
import com.joklek.cardintitysample.shop.impl.CartImpl;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

@SuppressWarnings("squid:S106")
public class ProductWindow extends AbstractWindow {

    @Autowired
    private ProductRepo productRepo;

    private final Map<Integer, String> options;

    public ProductWindow() {
        super();
        this.options = new HashMap<>();
        options.put(1, "Add Product");
        options.put(2, "Open Cart");
        options.put(3, "Exit");
    }

    @Override
    public String getTitle() {
        return "Shop window";
    }

    @Override
    public void render(Map<String, Object> args) {
        Cart cart = new CartImpl(UUID.randomUUID()); // TODO get from user
        while(true) {
            System.out.println("***" + getTitle() + "***");
            List<Product> products = productRepo.getProducts();
            if(!products.isEmpty()) {
                System.out.printf("There currently are %d products:%n", products.size());
            }
            else {
                System.out.println("There are no products currently");
            }
            for (int i = 0; i < products.size(); i++) {
                Product product = products.get(i);
                System.out.printf("%d. %s %s eur%n", i, product.getTitle(), product.getPrice().toPlainString());
            }
            System.out.println("Task menu:");

            for (Map.Entry<Integer, String> option : options.entrySet()) {
                System.out.printf("%d. %s%n", option.getKey(), option.getValue());
            }
            int choice = readNumber();
            switch(choice) {
                case 1:
                    addProduct(products, cart);
                    break;
                case 2:
                    openCart(cart);
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Incorrect input");
            }
        }
    }

    private void addProduct(List<Product> tasks, Cart cart) {
        System.out.println("Enter the id of product to add: ");
        Product product = getValidProduct(tasks);
        if(product != null) {
            cart.addProduct(product);
        }
    }

    private void openCart(Cart cart) {
        switchWindow("cartWindow", Collections.singletonMap("cart", cart));
    }

    private Product getValidProduct(List<Product> products){
        int choice = readNumber();
        if(choice < 0 || choice >= products.size()) {
            System.out.println("Incorrect id entered");
            return null;
        }
        else {
            return products.get(choice);
        }
    }
}
