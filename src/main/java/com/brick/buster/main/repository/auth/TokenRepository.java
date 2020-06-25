package com.brick.buster.main.repository.auth;

import com.brick.buster.main.domain.auth.TokenBlock;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository  extends CrudRepository<TokenBlock, Integer> {
    Optional<TokenBlock> findByBlocked(String token);
}
