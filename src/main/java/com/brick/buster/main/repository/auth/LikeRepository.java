package com.brick.buster.main.repository.auth;

import com.brick.buster.main.domain.auth.Like;
import com.brick.buster.main.domain.auth.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeRepository extends CrudRepository<Like, Integer> {
    Optional<Like> findByObjectAndReferenceAndUser(String object, String reference, User user);
}
