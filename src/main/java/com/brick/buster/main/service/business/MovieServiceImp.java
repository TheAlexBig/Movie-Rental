package com.brick.buster.main.service.business;

import com.brick.buster.main.domain.auth.Like;
import com.brick.buster.main.domain.auth.User;
import com.brick.buster.main.domain.business.Movie;
import com.brick.buster.main.form.MovieForm;
import com.brick.buster.main.form.MovieFormNoMulti;
import com.brick.buster.main.repository.business.MovieRepository;
import com.brick.buster.main.service.auth.LikeServiceImp;
import com.brick.buster.main.service.business.interfaces.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class MovieServiceImp implements MovieService {

    private final MovieRepository movieRepository;
    private final LikeServiceImp likeServiceImp;

    public MovieServiceImp(MovieRepository movieRepository, LikeServiceImp likeServiceImp) {
        this.movieRepository = movieRepository;
        this.likeServiceImp = likeServiceImp;
    }

    @Override
    public Optional<Movie> save(MultipartFile image,  MovieForm movieForm) {
        try {
            String uploadFileDirectory = "C:\\BrickBuster\\images\\";
            File imageFile = new File(uploadFileDirectory + image.getOriginalFilename());
            image.transferTo(imageFile);
        } catch (IOException e)
        {
            e.printStackTrace();
            return Optional.empty();
        }
        Movie newMovie = new Movie(movieForm, image.getOriginalFilename());
        return Optional.of(movieRepository.save(newMovie));
    }

    @Override
    public Movie save(MovieFormNoMulti movieFormNoMulti) {
        Movie newMovie = new Movie(movieFormNoMulti);
        return movieRepository.save(newMovie);
    }

    @Override
    public Movie save(Movie movie) {
        return movieRepository.save(movie);
    }

    @Override
    public Movie likeMovie(Movie movie, User user) {
        Optional<Like> optionalLike= likeServiceImp.findByObjectAndReferenceAndUser("movie", movie.getCode()+"", user);
        if(!optionalLike.isPresent()){
            likeServiceImp.save(new Like("movie", movie.getCode()+"", user));
            movie.likeMovie();
        }
        return movie;
    }

    @Override
    public void delete(Movie movie) {
        movieRepository.delete(movie);
    }


    @Override
    public Optional<Movie> findOne(Integer code) {
        return movieRepository.findById(code);
    }


    @Override
    public List<Movie> findAllByAvailablePublic(Pageable pageable, Integer page, Integer elements, String sort) {
        pageable = PageRequest.of(page, elements, Sort.by(sort));
        return movieRepository.findAllByAvailable(true, pageable);
    }

    @Override
    public List<Movie> findAllByAvailableAdmin(Pageable pageable, Integer page, Integer elements,
                                               String sort, String availability) {
        pageable = PageRequest.of(page, elements, Sort.by(sort));
        switch (availability){
            case "available":
                return movieRepository.findAllByAvailable(true, pageable);
            case "unavailable":
                return movieRepository.findAllByAvailable(false, pageable);
            default:
                return (List<Movie>) movieRepository.findAll(Sort.by(sort));
        }
    }

    @Override
    public List<Movie> findAllByTitleContainingAndAvailable(String name, Boolean available) {
        return movieRepository.findAllByTitleContainingAndAvailable(name, available);
    }

    @Override
    public List<Movie> findAllByTitleContaining(String name) {
        return movieRepository.findAllByTitleContaining(name);
    }

}
