package com.joklek.cardintitysample.user.impl;

import com.joklek.cardintitysample.user.CardInfo;

import java.util.Optional;

public class CardInfoImpl implements CardInfo {

    private String pan;
    private Integer cvc;
    private Integer expiryYear;
    private Integer expiryMonth;
    private String holder;

    private CardInfoImpl() {
    }

    @Override
    public String getPan() {
        return pan;
    }

    @Override
    public Integer getCvc() {
        return cvc;
    }

    @Override
    public Integer getExpiryYear() {
        return expiryYear;
    }

    @Override
    public Integer getExpiryMonth() {
        return expiryMonth;
    }

    @Override
    public String getHolder() {
        return holder;
    }

    private static class Builder {
        private Optional<String> pan;
        private Optional<Integer> cvc;
        private Optional<Integer> expiryYear;
        private Optional<Integer> expiryMonth;
        private Optional<String> holder;

        public Builder withCardHolder(String holder) {
            this.holder = Optional.of(holder);
            return this;
        }

        public Builder withPan(String pan) {
            this.pan = Optional.of(pan);
            return this;
        }

        public Builder withCvc(Integer cvc) {
            this.cvc = Optional.of(cvc);
            return this;
        }

        public Builder withExpiryYear(Integer expiryYear) {
            this.expiryYear = Optional.of(expiryYear);
            return this;
        }

        public Builder withExpiryMonth(Integer expiryMonth) {
            this.expiryMonth = Optional.of(expiryMonth);
            return this;
        }

        public CardInfoImpl build() {
            CardInfoImpl cardInfo = new CardInfoImpl();
            cardInfo.pan = pan.orElseThrow(IllegalStateException::new); // TODO make exceptions make sense
            cardInfo.cvc = cvc.orElseThrow(IllegalStateException::new); // TODO make exceptions make sense
            cardInfo.expiryYear = expiryYear.orElseThrow(IllegalStateException::new); // TODO make exceptions make sense
            cardInfo.expiryMonth = expiryMonth.orElseThrow(IllegalStateException::new); // TODO make exceptions make sense
            cardInfo.holder = holder.orElseThrow(IllegalStateException::new); // TODO make exceptions make sense
            return cardInfo;
        }
    }
}
