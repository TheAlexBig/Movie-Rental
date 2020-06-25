package com.brick.buster.main.response;

import com.brick.buster.main.response.interfaces.Response;

public class TokenResponse extends Response {
    private String token  = "";

    public TokenResponse(String message, String token) {
        super(message);
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
