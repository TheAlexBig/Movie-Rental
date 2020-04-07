package com.brick.buster.main.domain.business;

import com.brick.buster.main.service.business.interfaces.MovieService;
import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name="movie_log", schema = "public")
@SequenceGenerator(name="movie_log_sequence", sequenceName="movie_log_sequence")
public class MovieLog implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "movie_log_sequence")
    @Column(name = "movie_log_code")
    private int code = 0;
    @Column(name = "movie_log_title")
    private String title = "no-title";
    @Column(name = "movie_log_price")
    private BigDecimal price = BigDecimal.ZERO;
    @Column(name = "movie_log_rental")
    private BigDecimal rental = BigDecimal.ZERO;
    @Column(name = "movie_log_date_created")
    private String dateCreated = new Date().toString();

    @JsonBackReference(value = "movie-logs")
    @ManyToOne
    @JoinColumn(name="movie_code", nullable=false)
    private Movie movie;

    public MovieLog() {
    }

    public MovieLog(String title, BigDecimal price, BigDecimal rental, Movie movie) {
        this.title = title;
        this.price = price;
        this.rental = rental;
        this.movie = movie;
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getRental() {
        return rental;
    }

    public void setRental(BigDecimal rental) {
        this.rental = rental;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }
}
