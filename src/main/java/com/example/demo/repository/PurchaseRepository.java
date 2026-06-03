package com.example.demo.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import com.example.demo.model.Purchase;


public interface PurchaseRepository extends JpaRepository<Purchase, Integer>{
    List<Purchase> findByClientEmail(String email);
}
