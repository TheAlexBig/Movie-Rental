package com.brick.buster.main.repository.auth;

import com.brick.buster.main.domain.auth.Privilege;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PrivilegeRepository extends CrudRepository<Privilege, Integer> {
    Optional<Privilege> findByName(String name);
}
