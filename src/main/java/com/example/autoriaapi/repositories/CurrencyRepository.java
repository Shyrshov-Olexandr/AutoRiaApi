package com.example.autoriaapi.repositories;

import com.example.autoriaapi.requests.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CurrencyRepository extends JpaRepository<Currency, Long> {
    List<Currency> findByCcy(String ccy);
}