package com.brick.buster.main.domain.business;

import com.brick.buster.main.domain.auth.User;
import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

@Entity
@Table(name="rent", schema = "public")
@SequenceGenerator(name="rent_sequence", sequenceName="rent_sequence")
public class Rent implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rent_sequence")
    @Column(name = "rent_code")
    private int code;
    @Column(name = "rent_return_date")
    private String returnDate = new Date().toString();
    @Column(name = "rent_rented_date")
    private String dateRented = new Date().toString();
    @Column(name = "rent_returned_date")
    private String returnedDate = "not-returned";
    @Column(name = "rent_returned")
    private Boolean returned = false;
    @Column(name = "rent_fee")
    private BigDecimal fee = BigDecimal.ZERO;
    @Column(name = "rent_earned")
    private BigDecimal earned = BigDecimal.ZERO;
    @Column(name = "rent_price")
    private BigDecimal rentPrice = BigDecimal.ZERO;
    @Column(name = "rent_amount")
    private int amount = 1;

    @JsonBackReference(value = "movies-rented")
    @ManyToOne
    @JoinColumn(name="movie_code", nullable=false)
    private Movie movie;

    @JsonBackReference(value = "my-rents")
    @ManyToOne
    @JoinColumn(name="user_code", nullable=false)
    private User user;

    public Rent(){

    }

    public Rent(Movie currentMovie, User user, int amount) {
        Date currentDate = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(currentDate);
        this.movie = currentMovie;
        this.user = user;
        this.rentPrice = currentMovie.getRentalPrice();
        this.earned = currentMovie.getRentalPrice().multiply(BigDecimal.valueOf(amount));
        this.amount = amount;
        c.add(Calendar.DATE, 7);
        currentDate = c.getTime();
        this.returnDate = currentDate.toString();
    }

    public void calculateFee(){
        fee = earned.multiply(BigDecimal.valueOf(0.15));
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public String getDateRented() {
        return dateRented;
    }

    public void setDateRented(String dateRented) {
        this.dateRented = dateRented;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
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

    public String getReturnedDate() {
        return returnedDate;
    }

    public void setReturnedDate(String returnedDate) {
        this.returnedDate = returnedDate;
    }

    public BigDecimal getEarned() {
        return earned;
    }

    public void setEarned(BigDecimal earned) {
        this.earned = earned;
    }

    public Boolean getReturned() {
        return returned;
    }

    public void setReturned(Boolean returned) {
        this.returned = returned;
    }

    public BigDecimal getRentPrice() {
        return rentPrice;
    }

    public void setRentPrice(BigDecimal rentPrice) {
        this.rentPrice = rentPrice;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
