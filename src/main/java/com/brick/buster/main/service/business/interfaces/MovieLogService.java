package com.brick.buster.main.service.business.interfaces;

import com.brick.buster.main.domain.business.Movie;
import com.brick.buster.main.domain.business.MovieLog;

import java.math.BigDecimal;

public interface MovieLogService{
    MovieLog save(MovieLog movieLog);
    MovieLog save(String title, BigDecimal price, BigDecimal rental, Movie movie);
}
