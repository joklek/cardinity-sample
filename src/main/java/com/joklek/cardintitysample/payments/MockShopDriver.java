package com.joklek.cardintitysample.payments;

import com.cardinity.model.Payment;
import com.cardinity.model.Result;
import com.joklek.cardintitysample.user.CardInfo;

import java.math.BigDecimal;
import java.util.UUID;
// Todo this needs to be worked on
public class MockShopDriver implements ShopDriver {
    @Override
    public Result<Payment> makePayment(CardInfo cardInfo, BigDecimal price, UUID cartId) {
        return new Result<>(new Payment());
    }

    @Override
    public String getCallbackURL() {
        return "String";
    }

    @Override
    public Result<Payment> finalizePayment(UUID paymentId, String authorizeData) {
        return new Result<>(new Payment());
    }
}
