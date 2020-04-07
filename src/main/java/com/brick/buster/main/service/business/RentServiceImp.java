package com.brick.buster.main.service.business;

import com.brick.buster.main.domain.auth.User;
import com.brick.buster.main.domain.business.Movie;
import com.brick.buster.main.domain.business.Rent;
import com.brick.buster.main.repository.business.MovieRepository;
import com.brick.buster.main.repository.business.RentRepository;
import com.brick.buster.main.service.business.interfaces.RentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Service
public class RentServiceImp implements RentService {
    private final RentRepository rentRepository;
    private final MovieServiceImp movieServiceImp;

    @Autowired
    public RentServiceImp(RentRepository rentRepository, MovieServiceImp movieServiceImp) {
        this.rentRepository = rentRepository;
        this.movieServiceImp = movieServiceImp;
    }


    @Override
    public Rent save(Rent rent) {
        return rentRepository.save(rent);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Override
    public Optional<Rent> rentAndUpdateStock(Integer code, User user, int amount) {
        Optional<Movie> movie = movieServiceImp.findOne(code);
        if(movie.isPresent()
                && movie.get().getStock()>0
                && movie.get().getStock()>=amount
                && movie.get().getAvailable()){
            Movie currentMovie = movie.get();
            currentMovie.reduceStock(amount);
            movieServiceImp.save(currentMovie);
            Rent logRent = new Rent (currentMovie, user, amount);
            return Optional.of(rentRepository.save(logRent));
        }
        return Optional.empty();
    }

    @Override
    public Rent returnMovie(Rent rent) throws ParseException {
        Date current = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
        Date returnDate = formatter.parse(rent.getReturnDate());
        if(current.compareTo(returnDate)>0){
            rent.calculateFee();
        }
        else {
            rent.setFee(BigDecimal.ZERO);
        }
        rent.setReturnedDate(current.toString());
        rent.getMovie().addStock(rent.getAmount());
        rent.setReturned(true);
        rentRepository.save(rent);
        return rent;
    }

    @Override
    public Optional<Rent> findById(Integer code) {
        return rentRepository.findById(code);
    }

}
