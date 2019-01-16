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

    public void makePayment(CardInfo cardInfo, BigDecimal price, UUID cartId) {
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

        Result<Payment> result = client.createPayment(payment);

        /** Request was valid and payment was approved. */
        if (result.isValid() && result.getItem().getStatus() == Payment.Status.APPROVED) {
            UUID paymentId = result.getItem().getId();
            // proceed with successful payment flow
        }

        /** Request was valid but payment requires additional authentication. */
        else if (result.isValid() && result.getItem().getStatus() == Payment.Status.PENDING) {
            UUID paymentId = result.getItem().getId();
            String acsURL = result.getItem().getAuthorizationInformation().getUrl();
            String paReq = result.getItem().getAuthorizationInformation().getData();
            // redirect customer to ACS server for 3D Secure authentication
        }

        /** Request was valid but payment was declined. */
        else if (result.isValid()) {
            String declineReason = result.getItem().getError();
            // proceed with declined payment flow
        }

        /** Request was invalid.
         *   Possible reasons: wrong credentials, unsupported currency, suspended account, etc.
         */
        else {
            CardinityError error = result.getCardinityError();
            // log error details for debugging
            // proceed with error handling flow
        }
    }
}
