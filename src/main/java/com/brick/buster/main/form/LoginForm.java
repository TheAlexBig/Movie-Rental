package com.brick.buster.main.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class LoginForm {
    @NotEmpty(message = "Empty identifier")
    private String identifier = "";
    @NotEmpty(message = "Empty password")
    private String password = "";

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
