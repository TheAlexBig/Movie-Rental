package com.brick.buster.main.form;

import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class MovieFormNoMulti {
    @NotEmpty
    private String title = "no-title";

    @NotEmpty
    private String description = "no-description";

    @Range(min = 0)
    @NotNull
    private Integer stock = null;

    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal rentalPrice ;

    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal salePrice;

    @NotEmpty
    private String url = "none";

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

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
