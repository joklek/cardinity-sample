package com.joklek.cardintitysample.user;

public interface CardInfo {

    String getPan();
    Integer getCvc();
    Integer getExpiryYear();
    Integer getExpiryMonth();
    String getHolder();
}
