package com.brick.buster.main.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class UserForm {
    @NotEmpty(message = "Empty email")
    @Email(message = "Bad email format")
    private String email = "";
    @NotEmpty(message = "Empty password")
    private String password = "";
    @NotEmpty(message = "Empty username")
    private String username = "";

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
