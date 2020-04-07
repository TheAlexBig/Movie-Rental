package com.brick.buster.main.controller.movie;

import com.brick.buster.main.component.JWTTokenComponent;
import com.brick.buster.main.domain.auth.User;
import com.brick.buster.main.domain.business.Movie;
import com.brick.buster.main.domain.business.Purchase;
import com.brick.buster.main.domain.business.Rent;
import com.brick.buster.main.form.AmountForm;
import com.brick.buster.main.form.MovieForm;
import com.brick.buster.main.response.ObjectResponse;
import com.brick.buster.main.response.RequestResponse;
import com.brick.buster.main.response.interfaces.Response;
import com.brick.buster.main.service.auth.UserServiceImp;
import com.brick.buster.main.service.business.MovieLogServiceImp;
import com.brick.buster.main.service.business.MovieServiceImp;
import com.brick.buster.main.service.business.PurchaseServiceImp;
import com.brick.buster.main.service.business.RentServiceImp;
import com.brick.buster.main.util.ErrorValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.text.ParseException;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/commun/movies")
public class LoggedMovieController {
    private final RentServiceImp rentServiceImp;
    private final UserServiceImp userServiceImp;
    private final JWTTokenComponent jwtTokenComponent;
    private final PurchaseServiceImp purchaseServiceImp;

    private final ErrorValidator errorValidator;

    @Autowired
    public LoggedMovieController(UserServiceImp userServiceImp, JWTTokenComponent jwtTokenComponent, RentServiceImp rentServiceImp, PurchaseServiceImp purchaseServiceImp, ErrorValidator errorValidator) {
        this.userServiceImp = userServiceImp;
        this.jwtTokenComponent = jwtTokenComponent;
        this.rentServiceImp = rentServiceImp;
        this.purchaseServiceImp = purchaseServiceImp;
        this.errorValidator = errorValidator;
    }

    @PostMapping("/{code}/rent")
    public ResponseEntity<Response> rentMovie(@PathVariable Integer code, HttpServletRequest request,
                                              @Valid @RequestBody AmountForm amount, BindingResult bindingResult){
        Optional<Response> errors = errorValidator.verifyBindingResult(bindingResult);
        if(errors.isPresent()){
            return new ResponseEntity<>(errors.get(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
        String identifier = jwtTokenComponent.getIndentifierFromHttp(request);
        Optional<User> user = userServiceImp.findByUsernameOrEmail(identifier);
        if(user.isPresent()){
            Optional<Rent> rent = rentServiceImp.rentAndUpdateStock(code, user.get(), amount.getAmount());
            if(rent.isPresent()){
                return new ResponseEntity<>(new ObjectResponse(rent, "Successfully rented a movie. Enjoy!"),
                        HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(new RequestResponse("Error while trying to rent a movie"),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping("return/{code}")
    public ResponseEntity<Response> returnMovie(@PathVariable Integer code) throws ParseException {
        Optional<Rent> rent = rentServiceImp.findById(code);
        if(rent.isPresent() && !rent.get().getReturned()){
            rentServiceImp.returnMovie(rent.get());
            return new ResponseEntity<>(new ObjectResponse(rent, "Movie returned"),
                    HttpStatus.OK);
        }
        return new ResponseEntity<>(new RequestResponse("Error while returning a movie"),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/{code}/purchase")
    public ResponseEntity<Response> purchaseMovie(@PathVariable Integer code, HttpServletRequest request,
                                                  @Valid @RequestBody AmountForm amount, BindingResult bindingResult){
        Optional<Response> errors = errorValidator.verifyBindingResult(bindingResult);
        if(errors.isPresent()){
            return new ResponseEntity<>(errors.get(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
        String identifier = jwtTokenComponent.getIndentifierFromHttp(request);
        Optional<User> user = userServiceImp.findByUsernameOrEmail(identifier);
        if(user.isPresent()){
            Optional<Purchase> purchase = purchaseServiceImp.purchaseAndUpdateStock(code, user.get(), amount.getAmount());
            if(purchase.isPresent()){
                return new ResponseEntity<>(new ObjectResponse(purchase, "Movie successfully purchase. Enjoy!"),
                        HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(new RequestResponse("Error while trying to buy a movie"),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
