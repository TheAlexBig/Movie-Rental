package com.brick.buster.main.service.auth.interfaces;

import com.brick.buster.main.domain.auth.Privilege;
import org.springframework.stereotype.Service;

import java.util.Optional;

public interface PrivilegeService {
    Optional<Privilege> findByName(String name);
    Privilege save(String name);
}
