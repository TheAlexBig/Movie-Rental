package com.brick.buster.main.domain.business;

import com.brick.buster.main.domain.auth.User;
import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name="purchase", schema = "public")
@SequenceGenerator(name="purchase_sequence", sequenceName="purchase_sequence")
public class Purchase implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "purchase_sequence")
    @Column(name = "purchase_code")
    private int code;
    @Column(name = "purchase_date")
    private String purchaseDate = new Date().toString();
    @Column(name = "purchase_earned")
    private BigDecimal earned = BigDecimal.ZERO;
    @Column(name = "purchase_sale_price")
    private BigDecimal salePrice = BigDecimal.ZERO;
    @Column(name = "purchase_amount")
    private int amount = 1;
    @JsonBackReference(value = "movies-purchased")
    @ManyToOne
    @JoinColumn(name="movie_code", nullable=false)
    private Movie movie;

    @JsonBackReference(value = "my-purchases")
    @ManyToOne
    @JoinColumn(name="user_code", nullable=false)
    private User user;

    public Purchase(Movie currentMovie, User user, int amount) {
        this.movie = currentMovie;
        this.user = user;
        this.amount = amount;
        this.earned = movie.getSalePrice().multiply(BigDecimal.valueOf(amount));
        this.salePrice = movie.getSalePrice();
    }

    public Purchase() {
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public BigDecimal getEarned() {
        return earned;
    }

    public void setEarned(BigDecimal earned) {
        this.earned = earned;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }
}
