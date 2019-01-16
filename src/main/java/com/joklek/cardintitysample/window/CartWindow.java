package com.joklek.cardintitysample.window;

import com.joklek.cardintitysample.shop.Cart;
import com.joklek.cardintitysample.shop.Product;

import java.util.*;

@SuppressWarnings("squid:S106")
public class CartWindow extends AbstractWindow {

    private final Map<Integer, String> options;

    public CartWindow() {
        super();
        this.options = new HashMap<>();
        options.put(1, "Pay");
        options.put(2, "Clear Cart");
        options.put(3, "Back");
    }

    @Override
    public String getTitle() {
        return "Your cart";
    }

    @Override
    public void render(Map<String, Object> args) {
        Cart cart = (Cart) args.get("cart");
        while(true) {
            System.out.println("***" + getTitle() + "***");
            Map<Product, Integer> productsAndCounts = cart.getProductsAndAmounts();
            List<Product> products = new ArrayList<>(productsAndCounts.keySet());
            if(!productsAndCounts.isEmpty()) {
                System.out.printf("There currently are %d products:%n", cart.getTotalItems());
            }
            else {
                System.out.println("There are no products currently");
            }
            for (int i = 0; i < productsAndCounts.size(); i++) {
                Product product = products.get(i);
                int count = productsAndCounts.get(product);
                System.out.printf("%d. %s (total %d) %s eur%n", i, product.getTitle(), count, product.getPrice().toPlainString());
            }
            System.out.println(String.format("Total price: %s %s", cart.getTotalPrice().toPlainString(),"eur"));
            System.out.println("Task menu:");

            for (Map.Entry<Integer, String> option : options.entrySet()) {
                System.out.printf("%d. %s%n", option.getKey(), option.getValue());
            }
            int choice = readNumber();
            switch(choice) {
                case 1:
                    pay(cart);
                    break;
                case 2:
                    cart.clearCart();
                    return;
                case 3:
                    return;
                default:
                    System.out.println("Incorrect input");
            }
        }
    }

    private void pay(Cart cart) {
        switchWindow("payWindow", Collections.singletonMap("cart", cart));
    }
}
