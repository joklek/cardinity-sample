package com.joklek.cardintitysample;

import com.cardinity.CardinityClient;
import com.cardinity.model.Card;
import com.cardinity.model.CardinityError;
import com.cardinity.model.Payment;
import com.cardinity.model.Result;
import com.joklek.cardintitysample.shop.Cart;
import com.joklek.cardintitysample.shop.impl.CartImpl;
import com.joklek.cardintitysample.shop.impl.ProductImpl;
import com.joklek.cardintitysample.user.CardInfo;
import com.joklek.cardintitysample.user.impl.CardInfoImpl;
import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;
import java.util.UUID;

public class ShopDriver {

    @Value("${cardinity.consumer_key}")
    private String key;

    @Value("${cardinity.consumer_secret}")
    private String secret;

    @Value("${store.callback_url}")
    private String callbackURL;

    public void run() {
        Cart cart = new CartImpl(UUID.randomUUID());
        cart.addProduct(new ProductImpl(UUID.randomUUID(), "Test", BigDecimal.TEN));

        CardInfo cardInfo = new CardInfoImpl.Builder()
                .withCardHolder("James Bond")
                .withPan("4111111111111111")
                .withCvc(129)
                .withExpiryYear(2200)
                .withExpiryMonth(11)
                .build();

        makePayment(cardInfo, cart.getTotalPrice(), cart.getId());
    }

    private Card convertCard(CardInfo cardInfo) {
        Card card = new Card();
        card.setHolder(cardInfo.getHolder());
        card.setPan(cardInfo.getPan());
        card.setCvc(cardInfo.getCvc());
        card.setExpYear(cardInfo.getExpiryYear());
        card.setExpMonth(cardInfo.getExpiryMonth());
        return card;
    }

    public Result<Payment> makePayment(CardInfo cardInfo, BigDecimal price, UUID cartId) {
        // Move to field?
        CardinityClient client = new CardinityClient(key, secret);
        Payment payment = new Payment();
        payment.setCurrency("EUR");
        payment.setCountry("LT");
        payment.setPaymentMethod(Payment.Method.CARD);

        payment.setAmount(price);
        payment.setDescription(cartId.toString());
        Card card = convertCard(cardInfo);
        payment.setPaymentInstrument(card);

        return client.createPayment(payment);
    }

    public String getCallbackURL() {
        return callbackURL;
    }
}
