package com.brick.buster.main.service.auth.interfaces;

import com.brick.buster.main.domain.auth.User;
import com.brick.buster.main.form.UserForm;
import org.springframework.stereotype.Service;

import java.util.Optional;


public interface UserService {
    User save(User user);
    User save(UserForm userForm, String token);
    User saveNoCrypt(User user);
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Optional<User> findByUsernameOrEmail(String username, String email);
    Optional<User> findByUsernameOrEmail(String identifier);
    Optional<User> findByLoginToken(String token);
    Optional<User> validateUser(String identifier, String password);
    Optional<User> validateUser(String username, String email, String password);
    Boolean delete(String identifier);
}
