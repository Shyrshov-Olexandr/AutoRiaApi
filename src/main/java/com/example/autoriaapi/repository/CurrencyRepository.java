package com.example.autoriaapi.repository;

import com.example.autoriaapi.pojo.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CurrencyRepository extends JpaRepository<Currency, Long> {
    List<Currency> findByCcy(String ccy);
}