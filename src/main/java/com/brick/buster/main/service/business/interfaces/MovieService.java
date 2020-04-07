package com.brick.buster.main.service.business.interfaces;

import com.brick.buster.main.domain.auth.User;
import com.brick.buster.main.domain.business.Movie;
import com.brick.buster.main.form.MovieForm;
import com.brick.buster.main.form.MovieFormNoMulti;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

public interface MovieService {
    Optional<Movie> save(MultipartFile image,  MovieForm movieForm);
    Movie save(Movie movie);
    Movie save(MovieFormNoMulti movieFormNoMulti);
    Movie likeMovie(Movie movie, User user);
    void delete(Movie movie);
    Optional<Movie> findOne(Integer code);
    List<Movie> findAllByAvailablePublic(Pageable pageable, Integer page, Integer elements, String sort);
    List<Movie> findAllByAvailableAdmin(Pageable pageable, Integer page, Integer elements, String sort, String availability);
    List<Movie> findAllByTitleContainingAndAvailable(String name, Boolean available);
    List<Movie> findAllByTitleContaining(String name);
}
