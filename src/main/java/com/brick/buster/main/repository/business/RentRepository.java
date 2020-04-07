package com.brick.buster.main.repository.business;

import com.brick.buster.main.domain.business.Rent;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface RentRepository extends PagingAndSortingRepository<Rent, Integer> {
}
