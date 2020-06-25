package com.brick.buster.main.repository.business;

import com.brick.buster.main.domain.business.Purchase;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseRepository extends PagingAndSortingRepository<Purchase, Integer> {
}
