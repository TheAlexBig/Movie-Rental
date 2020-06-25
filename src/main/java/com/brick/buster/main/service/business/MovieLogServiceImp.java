package com.brick.buster.main.service.business;

import com.brick.buster.main.domain.business.Movie;
import com.brick.buster.main.domain.business.MovieLog;
import com.brick.buster.main.repository.business.MovieLogRepository;
import com.brick.buster.main.service.business.interfaces.MovieLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class MovieLogServiceImp implements MovieLogService {
    private final MovieLogRepository movieLogRepository;

    @Autowired
    public MovieLogServiceImp(MovieLogRepository movieLogRepository) {
        this.movieLogRepository = movieLogRepository;
    }

    @Override
    public MovieLog save(MovieLog movieLog) {
        return movieLogRepository.save(movieLog);
    }

    @Override
    public MovieLog save(String title, BigDecimal price, BigDecimal rental, Movie movie) {
        MovieLog newMovieLog = new MovieLog(title, price, rental, movie);
        return movieLogRepository.save(newMovieLog);
    }
}
