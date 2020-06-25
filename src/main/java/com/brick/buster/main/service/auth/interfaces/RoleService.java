package com.brick.buster.main.service.auth.interfaces;

import com.brick.buster.main.domain.auth.Privilege;
import com.brick.buster.main.domain.auth.Role;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    Optional<Role> findByName(String name);
    Role save(String name, List<Privilege> privileges);
}
