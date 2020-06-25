package com.brick.buster.main.repository.business;

import com.brick.buster.main.domain.business.Movie;
import com.brick.buster.main.domain.business.MovieLog;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieLogRepository extends PagingAndSortingRepository<MovieLog, Integer> {
}
