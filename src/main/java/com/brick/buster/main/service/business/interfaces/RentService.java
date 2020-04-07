package com.brick.buster.main.service.business.interfaces;

import com.brick.buster.main.domain.auth.User;
import com.brick.buster.main.domain.business.Movie;
import com.brick.buster.main.domain.business.Rent;

import java.text.ParseException;
import java.util.Optional;

public interface RentService {
    Rent save(Rent rent);
    Optional<Rent> rentAndUpdateStock(Integer code, User user, int amount);
    Optional<Rent> findById(Integer code);
    Rent returnMovie(Rent rent) throws ParseException;
}
