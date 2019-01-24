package com.joklek.cardintitysample.payments;

import com.cardinity.model.Payment;
import com.cardinity.model.Result;
import com.joklek.cardintitysample.user.CardInfo;

import java.math.BigDecimal;
import java.util.UUID;

public interface ShopDriver {
    Result<Payment> makePayment(CardInfo cardInfo, BigDecimal price, UUID cartId);
    String getCallbackURL();
    Result<Payment> finalizePayment(UUID paymentId, String authorizeData);
}
