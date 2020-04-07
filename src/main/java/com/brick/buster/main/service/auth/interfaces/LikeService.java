package com.brick.buster.main.service.auth.interfaces;

import com.brick.buster.main.domain.auth.Like;
import com.brick.buster.main.domain.auth.User;

import java.util.Optional;

public interface LikeService {
    Like save(Like like);
    Optional<Like> findByObjectAndReferenceAndUser(String object, String reference, User user);
}
