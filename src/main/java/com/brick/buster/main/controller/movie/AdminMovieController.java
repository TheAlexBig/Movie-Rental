package com.brick.buster.main.controller.movie;


import com.brick.buster.main.domain.business.Movie;
import com.brick.buster.main.domain.business.MovieLog;
import com.brick.buster.main.form.MovieForm;
import com.brick.buster.main.form.MovieFormNoMulti;
import com.brick.buster.main.response.ObjectResponse;
import com.brick.buster.main.response.RequestResponse;
import com.brick.buster.main.response.interfaces.Response;
import com.brick.buster.main.service.business.MovieLogServiceImp;
import com.brick.buster.main.service.business.MovieServiceImp;
import com.brick.buster.main.util.ErrorValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.swing.text.html.Option;
import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/admin/movies")
public class AdminMovieController {
    private final ObjectMapper objectMapper;
    private final MovieServiceImp movieServiceImp;
    private final MovieLogServiceImp movieLogServiceImp;
    private final ErrorValidator errorValidator;

    @Autowired
    public AdminMovieController(MovieServiceImp movieServiceImp, ObjectMapper objectMapper, MovieLogServiceImp movieLogServiceImp, ErrorValidator errorValidator) {
        this.movieServiceImp = movieServiceImp;
        this.objectMapper = objectMapper;
        this.movieLogServiceImp = movieLogServiceImp;
        this.errorValidator = errorValidator;
    }

    @PostMapping("/special")
    public ResponseEntity<Response> addMovie(@RequestPart MultipartFile image, @Valid @ModelAttribute MovieForm movieForm,
                                             BindingResult bindingResult){
        Optional<Response> errors = errorValidator.verifyBindingResult(bindingResult);
        if(errors.isPresent()){
            return new ResponseEntity<>(errors.get(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
        if(!image.isEmpty()){
            Optional<Movie> result = movieServiceImp.save(image,movieForm);
            if(result.isPresent()){
                return new ResponseEntity<>(new RequestResponse("Movie created"), HttpStatus.CREATED);
            }
        }
        return new ResponseEntity<>(new RequestResponse("Error while trying to create movie"), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping
    public ResponseEntity<Response> addMovieNoMulti(@Valid @RequestBody MovieFormNoMulti movieFormNoMulti,
                                             BindingResult bindingResult){
        Optional<Response> errors = errorValidator.verifyBindingResult(bindingResult);
        if(errors.isPresent()){
            return new ResponseEntity<>(errors.get(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
        Movie movie = movieServiceImp.save(movieFormNoMulti);
        return new ResponseEntity<>(new ObjectResponse(movie, "Movie created"), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{code}")
    public ResponseEntity<Response> updateMovie(@PathVariable Integer code, HttpServletRequest request) throws IOException {
        Optional<Movie> movie = movieServiceImp.findOne(code);
        if(movie.isPresent()){
            String title = movie.get().getTitle();
            BigDecimal rentalDecimal = movie.get().getRentalPrice();
            BigDecimal priceDecimal = movie.get().getSalePrice();
            Movie updatedMovie = objectMapper.readerForUpdating(movie.get()).readValue(request.getReader());
            if(!title.equals(updatedMovie.getTitle())
                    || !rentalDecimal.equals(updatedMovie.getRentalPrice()) || !priceDecimal.equals(updatedMovie.getSalePrice())){
                movieLogServiceImp.save(title, rentalDecimal,  priceDecimal, updatedMovie);
            }
            movieServiceImp.save(updatedMovie);
            return new ResponseEntity<>(new ObjectResponse(movie.get(), "Movie updated"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(new RequestResponse("Error while trying to update movie"), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping(value = "/all")
    public List<Movie> getAllAvailable
            (Pageable pageable, @RequestParam(value = "page", defaultValue = "0") Integer page,
             @RequestParam(value = "elements", defaultValue = "5") Integer elements,
             @RequestParam(value = "sort", defaultValue = "title" ) String sort,
             @RequestParam(value ="availability", defaultValue = "none") String availability){
        return movieServiceImp.findAllByAvailableAdmin(pageable, page, elements, sort, availability);
    }
    @GetMapping(value = "/search")
    public List<Movie> getByAnime(@RequestParam(value = "name" ,defaultValue = "") String name){
        return movieServiceImp.findAllByTitleContaining(name);
    }

    @DeleteMapping(value = "/{code}")
    public ResponseEntity<Response> deleteMovie(@PathParam(value = "code") Integer code){
        Optional<Movie> movie = movieServiceImp.findOne(code);
        if(movie.isPresent()){
            movieServiceImp.delete(movie.get());
            return new ResponseEntity<>(new RequestResponse("Movie deleted"), HttpStatus.OK);
        }
        return new ResponseEntity<>(new RequestResponse("Error while trying to delete a movie"), HttpStatus.OK);
    }

}
