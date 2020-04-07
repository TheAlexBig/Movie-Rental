package com.brick.buster.main.domain.auth;


import com.brick.buster.main.domain.business.Purchase;
import com.brick.buster.main.domain.business.Rent;
import com.brick.buster.main.form.UserForm;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "user", schema = "public")
@SequenceGenerator(name="user_sequence", sequenceName="user_sequence")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence")
    @Column(name = "user_code")
    private int code = 0;
    @Column(name = "user_email")
    private String email = "";
    @Column(name = "user_username")
    private String username = "";
    @Column(name = "user_password")
    private String password = "";
    @Column(name = "user_token_reset", length = 1024)
    private String resetToken = "";
    @Column(name = "user_login_token", length = 1024)
    private String loginToken = "";
    @Column(name = "user_active")
    private boolean activeAccount = false;
    @Column(name = "user_date_created")
    private String dateCreated = new Date().toString();

    @ManyToMany
    @JoinTable(
            name = "user_role",
            joinColumns =  @JoinColumn(name = "user_id", referencedColumnName = "user_code"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "role_code"))
    private List<Role> roles = null;

    @JsonManagedReference(value = "my-rents")
    @OneToMany(mappedBy="user")
    private Set<Rent> rented = null;

    @JsonManagedReference(value = "my-purchases")
    @OneToMany(mappedBy="user")
    private Set<Purchase> purchases = null;

    public User() {
    }

    public User(String email, String username, String password, String loginToken, List<Role> roles) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.loginToken = loginToken;
        this.roles = roles;
    }

    public User(String email, String username, String password, Role role){
        this.email = email;
        this.username = username;
        this.password = password;
        this.roles = Collections.singletonList(role);
    }

    public User(String email, String username, String password, String loginToken) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.loginToken = loginToken;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getResetToken() {
        return resetToken;
    }

    public void setResetToken(String resetToken) {
        this.resetToken = resetToken;
    }

    public String getLoginToken() {
        return loginToken;
    }

    public void setLoginToken(String loginToken) {
        this.loginToken = loginToken;
    }

    public boolean isActiveAccount() {
        return activeAccount;
    }

    public void setActiveAccount(boolean activeAccount) {
        this.activeAccount = activeAccount;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public Set<Rent> getRented() {
        return rented;
    }

    public void setRented(Set<Rent> rented) {
        this.rented = rented;
    }
}
