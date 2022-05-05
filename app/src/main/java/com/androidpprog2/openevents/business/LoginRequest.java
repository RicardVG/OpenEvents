package com.androidpprog2.openevents.business;

import java.io.Serializable;

public class LoginRequest implements Serializable {
    private String email;
    private String password;
    private String accessToken;


    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
    public String getAccessToken() {
        return accessToken;
    }

}
