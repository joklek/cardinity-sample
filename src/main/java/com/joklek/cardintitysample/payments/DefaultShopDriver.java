package com.joklek.cardintitysample.payments;

import com.cardinity.CardinityClient;
import com.cardinity.model.Card;
import com.cardinity.model.Payment;
import com.cardinity.model.Result;
import com.joklek.cardintitysample.user.CardInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;
import java.util.UUID;

public class DefaultShopDriver implements ShopDriver {

    @Autowired
    CardinityClient client;

    @Value("${store.callback_url:http://undefined.null}")
    private String callbackURL;

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

    public Result<Payment> finalizePayment(UUID paymentId, String authorizeData) {
        return client.finalizePayment(paymentId, authorizeData);
    }

    public String getCallbackURL() {
        return callbackURL;
    }
}
