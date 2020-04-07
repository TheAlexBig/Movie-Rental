package com.brick.buster.main.controller.movie;

import com.brick.buster.main.domain.business.Movie;
import com.brick.buster.main.response.RequestResponse;
import com.brick.buster.main.response.interfaces.Response;
import com.brick.buster.main.service.business.MovieServiceImp;
import com.brick.buster.main.util.Views;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/v1/public/movies")
public class PublicMovieController {
    private final MovieServiceImp movieServiceImp;

    @Autowired
    public PublicMovieController(MovieServiceImp movieServiceImp) {
        this.movieServiceImp = movieServiceImp;
    }

    @JsonView(Views.PublicMovies.class)
    @GetMapping(value = "/all")
    public List<Movie> getAllAvailable
            (Pageable pageable, @RequestParam(value = "page", defaultValue = "0") Integer page,
             @RequestParam(value = "elements", defaultValue = "5") Integer elements,
            @RequestParam(value = "sort", defaultValue = "title") String sort,
             @RequestParam(value = "name" ,defaultValue = "") String name){
        return movieServiceImp.findAllByAvailablePublic(pageable, page, elements, sort);
    }

    @JsonView(Views.PublicMovies.class)
    @GetMapping(value = "/search")
    public List<Movie> getByName(@RequestParam(value = "name" ,defaultValue = "") String name){
        return movieServiceImp.findAllByTitleContainingAndAvailable(name, true);
    }
}
