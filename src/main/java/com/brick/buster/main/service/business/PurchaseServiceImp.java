package com.brick.buster.main.service.business;

import com.brick.buster.main.domain.auth.User;
import com.brick.buster.main.domain.business.Movie;
import com.brick.buster.main.domain.business.Purchase;
import com.brick.buster.main.repository.business.PurchaseRepository;
import com.brick.buster.main.service.business.interfaces.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class PurchaseServiceImp implements PurchaseService {
    private final PurchaseRepository purchaseRepository;
    private final MovieServiceImp movieServiceImp;

    public PurchaseServiceImp(PurchaseRepository purchaseRepository, MovieServiceImp movieServiceImp) {
        this.purchaseRepository = purchaseRepository;
        this.movieServiceImp = movieServiceImp;
    }

    @Override
    public Purchase save(Purchase rent) {
        return null;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Override
    public Optional<Purchase> purchaseAndUpdateStock(Integer code, User user, int amount) {
        Optional<Movie> movie = movieServiceImp.findOne(code);
        if(movie.isPresent()
                && movie.get().getStock()>0
                && movie.get().getAvailable()
                && movie.get().getStock()>=amount){
            Movie currentMovie = movie.get();
            currentMovie.reduceStock(amount);
            movieServiceImp.save(currentMovie);
            Purchase logPurchase = new Purchase (currentMovie, user, amount);
            return Optional.of(purchaseRepository.save(logPurchase));
        }
        return Optional.empty();
    }
}
