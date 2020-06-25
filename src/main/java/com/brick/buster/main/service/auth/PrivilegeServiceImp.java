package com.brick.buster.main.service.auth;

import com.brick.buster.main.domain.auth.Privilege;
import com.brick.buster.main.repository.auth.PrivilegeRepository;
import com.brick.buster.main.service.auth.interfaces.PrivilegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PrivilegeServiceImp implements PrivilegeService {

    private PrivilegeRepository privilegeRepository;

    @Autowired
    public PrivilegeServiceImp(PrivilegeRepository privilegeRepository){
        this.privilegeRepository = privilegeRepository;
    }

    @Override
    public Optional<Privilege> findByName(String name) {
        return privilegeRepository.findByName(name);
    }

    @Override
    public Privilege save(String name) {
        Privilege privilege = new Privilege(name);
        return privilegeRepository.save(privilege);
    }
}
