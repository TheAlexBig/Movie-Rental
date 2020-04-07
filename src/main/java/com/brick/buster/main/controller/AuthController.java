package com.brick.buster.main.controller;

import com.brick.buster.main.component.JWTTokenComponent;
import com.brick.buster.main.domain.auth.User;
import com.brick.buster.main.form.LoginForm;
import com.brick.buster.main.form.UserForm;
import com.brick.buster.main.response.RequestResponse;
import com.brick.buster.main.response.TokenResponse;
import com.brick.buster.main.response.interfaces.Response;
import com.brick.buster.main.service.auth.UserServiceImp;
import com.brick.buster.main.service.auth.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {
    @Value("{jwt.prefix}")
    private String PREFIX;

    private final UserServiceImp userServiceImp;
    private final JWTTokenComponent jwtTokenComponent;

    public AuthController(UserServiceImp userServiceImp, JWTTokenComponent jwtTokenComponent) {
        this.userServiceImp = userServiceImp;
        this.jwtTokenComponent = jwtTokenComponent;
    }

    @PostMapping(value = "/login")
    public ResponseEntity<Response> loginProcess(@Valid @RequestBody LoginForm loginForm , BindingResult bindingResult){

        Optional<User> optionalUser = userServiceImp.validateUser(loginForm.getIdentifier(), loginForm.getPassword());
        if(optionalUser.isPresent()){
            String token =  jwtTokenComponent.generateToken(loginForm.getIdentifier());
            optionalUser.get().setLoginToken(token.replace(PREFIX+" ", ""));
            userServiceImp.saveNoCrypt(optionalUser.get());
            return new ResponseEntity<>(new TokenResponse("Here is your token", token), HttpStatus.OK);

        }
        return new ResponseEntity<>(new RequestResponse("Invalid credentials"), HttpStatus.UNAUTHORIZED);
    }


}
