package com.brick.buster.main.service.auth;


import com.brick.buster.main.domain.auth.Role;
import com.brick.buster.main.domain.auth.User;
import com.brick.buster.main.form.UserForm;
import com.brick.buster.main.repository.auth.UserRepository;
import com.brick.buster.main.service.auth.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class UserServiceImp implements UserService {
    private final RoleServiceImp roleServiceImp;

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImp(RoleServiceImp roleServiceImp, UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.roleServiceImp = roleServiceImp;
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public User save(User user) {
        String password = user.getPassword();
        String passwordCrypt = bCryptPasswordEncoder.encode(password);
        user.setPassword(passwordCrypt);
        return userRepository.save(user);
    }

    @Override
    public User save(UserForm userForm, String token) {
        Optional<Role> roles = roleServiceImp.findByName("ROLE_USER");
        String password = userForm.getPassword();
        String passwordCrypt = bCryptPasswordEncoder.encode(password);
        userForm.setPassword(passwordCrypt);
        User user;

        user = roles.map(role ->
                new User(userForm.getEmail(), userForm.getUsername(), userForm.getPassword(), token, Collections.singletonList(role)))
                .orElseGet(() -> new User(userForm.getEmail(), userForm.getUsername(), userForm.getPassword(), token));
        return userRepository.save(user);
    }

    @Override
    public User saveNoCrypt(User user) {
        return userRepository.save(user);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> findByUsernameOrEmail(String username, String email) {
        return userRepository.findByUsernameOrEmail(username, email);
    }

    @Override
    public Optional<User> findByUsernameOrEmail(String identifier) {
        return userRepository.findByUsernameOrEmail(identifier, identifier);
    }

    @Override
    public Optional<User> findByLoginToken(String token) {
        return userRepository.findByLoginToken(token);
    }

    @Override
    public Optional<User> validateUser(String identifier, String password) {
        Optional<User> user = userRepository.findByUsernameOrEmail(identifier, identifier);
        if(user.isPresent() && bCryptPasswordEncoder.matches(password, user.get().getPassword())){
            return user;
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> validateUser(String username, String email, String password) {
        Optional<User> user = userRepository.findByUsernameOrEmail(username, email);
        if(user.isPresent() && bCryptPasswordEncoder.matches(password, user.get().getPassword())){
            return user;
        }
        return Optional.empty();
    }

    @Override
    public Boolean delete(String identifier) {
        Optional<User> user = userRepository.findByUsernameOrEmail(identifier, identifier);
        if(user.isPresent()){
            userRepository.delete(user.get());
            return true;
        }
        return false;
    }
}
