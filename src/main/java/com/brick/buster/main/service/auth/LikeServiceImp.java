package com.brick.buster.main.service.auth;

import com.brick.buster.main.domain.auth.Like;
import com.brick.buster.main.domain.auth.User;
import com.brick.buster.main.repository.auth.LikeRepository;
import com.brick.buster.main.service.auth.interfaces.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LikeServiceImp  implements LikeService {
    private final LikeRepository likeRepository;

    public LikeServiceImp(LikeRepository likeRepository) {
        this.likeRepository = likeRepository;
    }

    @Override
    public Like save(Like like) {
        return likeRepository.save(like);
    }

    @Override
    public Optional<Like> findByObjectAndReferenceAndUser(String object, String reference, User user) {
        return likeRepository.findByObjectAndReferenceAndUser(object, reference, user);
    }
}
