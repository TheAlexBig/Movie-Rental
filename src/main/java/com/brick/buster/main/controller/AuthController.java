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
import com.brick.buster.main.util.ErrorValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {
    @Value("{jwt.prefix}")
    private String PREFIX;
    private final ErrorValidator errorValidator;
    private final UserServiceImp userServiceImp;
    private final JWTTokenComponent jwtTokenComponent;

    public AuthController(UserServiceImp userServiceImp, JWTTokenComponent jwtTokenComponent, ErrorValidator errorValidator) {
        this.userServiceImp = userServiceImp;
        this.jwtTokenComponent = jwtTokenComponent;
        this.errorValidator = errorValidator;
    }

    @PostMapping(value = "/login")
    public ResponseEntity<Response> loginProcess(@Valid @RequestBody LoginForm loginForm , BindingResult bindingResult){
        Optional<Response> errors = errorValidator.verifyBindingResult(bindingResult);
        if(errors.isPresent()){
            return new ResponseEntity<>(errors.get(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
        Optional<User> optionalUser = userServiceImp.validateUser(loginForm.getIdentifier(), loginForm.getPassword());
        if(optionalUser.isPresent()){
            String token =  jwtTokenComponent.generateToken(loginForm.getIdentifier());
            optionalUser.get().setLoginToken(token.replace(PREFIX+" ", ""));
            userServiceImp.saveNoCrypt(optionalUser.get());
            return new ResponseEntity<>(new TokenResponse("Here is your token", token), HttpStatus.OK);

        }
        return new ResponseEntity<>(new RequestResponse("Invalid credentials"), HttpStatus.UNAUTHORIZED);
    }
    @PostMapping(value = "/register")
    public ResponseEntity<Response> registerProcess(@Valid @RequestBody UserForm userForm , BindingResult bindingResult){
        Optional<Response> errors = errorValidator.verifyBindingResult(bindingResult);
        if(errors.isPresent()){
            return new ResponseEntity<>(errors.get(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
        Optional<User> optionalUser = userServiceImp.findByUsernameOrEmail(userForm.getUsername(), userForm.getEmail());
        if(optionalUser.isPresent()){
            return new ResponseEntity<>(new RequestResponse("Email/username in use" ), HttpStatus.UNAUTHORIZED);
        }
        String token =  jwtTokenComponent.generateToken(userForm.getUsername());
        userServiceImp.save(userForm, token);
        return new ResponseEntity<>(new RequestResponse("User created"), HttpStatus.OK);
    }

    @PostMapping(value = "/logout")
    public ResponseEntity<Response> logoutProcess(HttpServletRequest request){
        jwtTokenComponent.blockToken(request);
        return new ResponseEntity<>(new RequestResponse("Your token has been block"), HttpStatus.OK);
    }

}
