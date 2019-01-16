package com.joklek.cardintitysample.window;

import com.joklek.cardintitysample.shop.Cart;
import com.joklek.cardintitysample.user.CardInfo;
import com.joklek.cardintitysample.user.impl.CardInfoImpl;

import java.math.BigDecimal;
import java.util.*;

@SuppressWarnings("squid:S106")
public class PayWindow extends AbstractWindow {

    private final Map<Integer, String> options;

    public PayWindow() {
        super();
        this.options = new HashMap<>();
        options.put(1, "Add card holders name");
        options.put(2, "Add PAN number");
        options.put(3, "Add CVC number");
        options.put(4, "Add expiry year");
        options.put(5, "Add expiry month");
        options.put(6, "Process");
        options.put(7, "Back");
    }

    @Override
    public String getTitle() {
        return "Payment window";
    }

    @Override
    public void render(Map<String, Object> args) {
        Cart cart = (Cart) args.get("cart");
        CardInfoImpl.Builder cardBuilder = new CardInfoImpl.Builder();
        while(true) {
            System.out.println("***" + getTitle() + "***");
            for (Map.Entry<Integer, String> option : options.entrySet()) {
                System.out.printf("%d. %s%n", option.getKey(), option.getValue());
            }
            int choice = readNumber();
            switch(choice) {
                case 1:
                    enterHolderName(cardBuilder);
                    break;
                case 2:
                    enterPanNumber(cardBuilder);
                    return;
                case 3:
                    enterCVCNumber(cardBuilder);
                    return;
                case 4:
                    enterExpiryYear(cardBuilder);
                    return;
                case 5:
                    enterExpiryMonth(cardBuilder);
                    return;
                case 6:
                    process(cardBuilder.build(), cart.getTotalPrice(), cart.getId());
                    return;
                case 7:
                    return;
                default:
                    System.out.println("Incorrect input");
            }
        }
    }

    private void enterHolderName(CardInfoImpl.Builder cardBuilder) {
        System.out.println("Enter the cardholders first and last names");
        String pan = readInput();
        cardBuilder.withCardHolder(pan);

        System.out.println("Enter 'n' to return to the payment menu, or anything else to continue");
        String input = readInput();
        if(input.trim().equals("n")) {
            return;
        }
        enterPanNumber(cardBuilder);
    }

    private void enterPanNumber(CardInfoImpl.Builder cardBuilder) {
        System.out.println("Enter the PAN of your card");
        String pan = readInput();
        cardBuilder.withPan(pan);

        System.out.println("Enter 'n' to return to the payment menu, or anything else to continue");
        String input = readInput();
        if(input.trim().equals("n")) {
            return;
        }
        enterCVCNumber(cardBuilder);
    }

    private void enterCVCNumber(CardInfoImpl.Builder cardBuilder) {
        System.out.println("Enter the CVC of your card");
        Integer cvc = readNumber();
        cardBuilder.withCvc(cvc);

        System.out.println("Enter 'n' to return to the payment menu, or anything else to continue");
        String input = readInput();
        if(input.trim().equals("n")) {
            return;
        }
        enterExpiryYear(cardBuilder);
    }

    private void enterExpiryYear(CardInfoImpl.Builder cardBuilder) {
        System.out.println("Enter the expiry year of your card");
        Integer expiryYear = readNumber();
        cardBuilder.withExpiryYear(expiryYear);

        System.out.println("Enter 'n' to return to the payment menu, or anything else to continue");
        String input = readInput();
        if(input.trim().equals("n")) {
            return;
        }
        enterExpiryMonth(cardBuilder);
    }

    private void enterExpiryMonth(CardInfoImpl.Builder cardBuilder) {
        System.out.println("Enter the expiry month of your card");
        Integer expiryMonth = readNumber();
        cardBuilder.withExpiryMonth(expiryMonth);
    }

    private void process(CardInfo card, BigDecimal price, UUID cartId) {
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("card", card);
        arguments.put("price", price);
        arguments.put("cartId", cartId);
        switchWindow("processingWindow", arguments);
    }
}
