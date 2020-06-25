package com.brick.buster.main.service.auth;

import com.brick.buster.main.domain.auth.Privilege;
import com.brick.buster.main.domain.auth.Role;
import com.brick.buster.main.repository.auth.RoleRepository;
import com.brick.buster.main.service.auth.interfaces.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImp implements RoleService {


    private RoleRepository roleRepository;

    @Autowired
    public RoleServiceImp(RoleRepository roleRepository){
        this.roleRepository = roleRepository;
    }

    @Override
    public Optional<Role> findByName(String name) {
        return roleRepository.findByName(name);
    }

    @Override
    public Role save(String name, List<Privilege> privileges) {
        Role role = new Role(name, privileges);
        return roleRepository.save(role);
    }
}
