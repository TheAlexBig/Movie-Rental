package com.brick.buster.main.repository.business;

import com.brick.buster.main.domain.business.Movie;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends PagingAndSortingRepository<Movie, Integer> {
    List<Movie> findAllByAvailable(Boolean available, Pageable pageable);
    List<Movie> findAllByTitleContaining(String name);

    List<Movie> findAllByTitleContainingAndAvailable(String name, Boolean available);
}
