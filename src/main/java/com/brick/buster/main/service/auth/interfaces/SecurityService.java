package com.brick.buster.main.service.auth.interfaces;

import org.springframework.stereotype.Service;


public interface SecurityService {
    String findLoggedInUser();
    void autoLogin(String identifier, String password);
}
