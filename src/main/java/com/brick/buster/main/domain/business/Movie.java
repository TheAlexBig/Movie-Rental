package com.brick.buster.main.domain.business;

import com.brick.buster.main.form.MovieForm;
import com.brick.buster.main.form.MovieFormNoMulti;
import com.brick.buster.main.util.Views;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name="movie", schema = "public")
@SequenceGenerator(name="movie_sequence", sequenceName="movie_sequence")
public class Movie implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "movie_sequence")
    @JsonView(Views.PublicMovies.class)
    @Column(name = "movie_code")
    private int code = 0;
    @JsonView(Views.PublicMovies.class)
    @Column(name = "movie_title")
    private String title = "no-title";
    @JsonView(Views.PublicMovies.class)
    @Column(name = "movie_description")
    private String description = "no-description";
    @Column(name = "movie_available")
    private Boolean available = false;
    @JsonView(Views.PublicMovies.class)
    @Column(name = "movie_stock")
    private int stock = 0;
    @JsonView(Views.PublicMovies.class)
    @Column(name = "movie_rental")
    private BigDecimal rentalPrice = BigDecimal.ZERO;
    @JsonView(Views.PublicMovies.class)
    @Column(name = "movie_sale")
    private BigDecimal salePrice = BigDecimal.ZERO;
    @JsonView(Views.PublicMovies.class)
    @Column(name = "movie_image")
    private String imageName = "none";
    @JsonView(Views.PublicMovies.class)
    @Column(name = "movie_likes")
    private int likes = 0;

    @JsonManagedReference(value = "movie-logs")
    @OneToMany(mappedBy="movie")
    private Set<MovieLog> logs = null;

    @JsonManagedReference(value = "movies-rented")
    @OneToMany(mappedBy="movie")
    private Set<Rent> rented = null;

    @JsonManagedReference(value = "movies-purchased")
    @OneToMany(mappedBy="movie")
    private Set<Purchase> purchased = null;

    public Movie() {
    }

    public Movie(String title, String description, Boolean available, int stock,
                 BigDecimal rentalPrice, BigDecimal salePrice, String imageName) {
        this.title = title;
        this.description = description;
        this.available = available;
        this.stock = stock;
        this.rentalPrice = rentalPrice;
        this.salePrice = salePrice;
        this.imageName = imageName;
    }

    public Movie(MovieForm movieForm, String imageName) {
        this.title = movieForm.getTitle();
        this.description = movieForm.getDescription();
        this.stock = movieForm.getStock();
        this.rentalPrice = movieForm.getRentalPrice()   ;
        this.salePrice = movieForm.getSalePrice();
        this.imageName = imageName;
    }

    public Movie(MovieFormNoMulti movieFormNoMulti) {
        this.title = movieFormNoMulti.getTitle();
        this.description = movieFormNoMulti.getDescription();
        this.stock = movieFormNoMulti.getStock();
        this.rentalPrice = movieFormNoMulti.getRentalPrice()   ;
        this.salePrice = movieFormNoMulti.getSalePrice();
        this.imageName = movieFormNoMulti.getUrl();
    }

    public void likeMovie(){
        this.likes+=1;
    }

    public void reduceStock(int amount){
        this.stock-=amount;
    }
    public void addStock(int amount){
        this.stock+=amount;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean availability) {
        this.available = availability;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public BigDecimal getRentalPrice() {
        return rentalPrice;
    }

    public void setRentalPrice(BigDecimal rentalPrice) {
        this.rentalPrice = rentalPrice;
    }

    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public Set<MovieLog> getLogs() {
        return logs;
    }

    public void setLogs(Set<MovieLog> logs) {
        this.logs = logs;
    }

    public Set<Rent> getRented() {
        return rented;
    }

    public void setRented(Set<Rent> rented) {
        this.rented = rented;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public Set<Purchase> getPurchased() {
        return purchased;
    }

    public void setPurchased(Set<Purchase> purchased) {
        this.purchased = purchased;
    }
}
