package com.joklek.cardintitysample;

import com.cardinity.CardinityClient;
import com.cardinity.model.Card;
import com.cardinity.model.CardinityError;
import com.cardinity.model.Payment;
import com.cardinity.model.Result;
import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;
import java.util.UUID;

public class ShopDriver {

    @Value("${cardinity.consumer_key}")
    private String key;

    @Value("${cardinity.consumer_secret}")
    private String secret;

    public void run() {
        CardinityClient client = new CardinityClient(key, secret);

        Payment payment = new Payment();
        payment.setAmount(new BigDecimal(10));
        payment.setCurrency("EUR");
        payment.setCountry("LT");
        payment.setPaymentMethod(Payment.Method.CARD);
        Card card = new Card();
        card.setPan("4111111111111111");
        card.setCvc(123);
        card.setExpYear(2021);
        card.setExpMonth(1);
        card.setHolder("John Doe");
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
