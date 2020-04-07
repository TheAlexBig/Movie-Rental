package com.brick.buster.main.service.business.interfaces;

import com.brick.buster.main.domain.auth.User;
import com.brick.buster.main.domain.business.Purchase;

import java.util.Optional;

public interface PurchaseService {
    Purchase save(Purchase rent);
    Optional<Purchase> purchaseAndUpdateStock(Integer code, User user, int Amount);
}
