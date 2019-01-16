package com.joklek.cardintitysample.user.impl;

import com.joklek.cardintitysample.user.UserInfo;

import java.util.Optional;

public class UserInfoImpl implements UserInfo {

    private UserInfoImpl() {
    }

    private String firstName;
    private String lastName;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    private static class Builder {
        Optional<String> firstName;
        Optional<String> lastName;

        public Builder withName(String name) {
            firstName = Optional.of(name);
            return this;
        }

        public Builder withLastName(String lastName) {
            firstName = Optional.of(lastName);
            return this;
        }

        public UserInfoImpl build() {
            UserInfoImpl userInfo = new UserInfoImpl();
            userInfo.firstName = firstName.orElseThrow(IllegalStateException::new); // TODO make exceptions make sense
            userInfo.lastName = firstName.orElseThrow(IllegalStateException::new);
            return userInfo;
        }
    }
}
