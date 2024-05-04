package com.example.eback.repository;

import com.example.eback.entity.Shopcartnum;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopcartnumRepository extends JpaRepository<Shopcartnum, Integer> {
    boolean existsByUserId(int userId);

    Shopcartnum getShopcartnumByUserIdAndBookId(int userId, int bookId);
}
