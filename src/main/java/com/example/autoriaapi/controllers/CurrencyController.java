package com.example.autoriaapi.controllers;


import com.example.autoriaapi.models.CarUser;
import com.example.autoriaapi.models.ECurrency;
import com.example.autoriaapi.pojo.Currency;
import com.example.autoriaapi.repository.CarRepository;
import com.example.autoriaapi.repository.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class CurrencyController {
    @Autowired
    CurrencyRepository currencyRepository;
    @Autowired
    CarRepository carRepository;

    private static final String PRIVATBANK_API_URL = "https://api.privatbank.ua/p24api/pubinfo?exchange&coursid=11";

    private final RestTemplate restTemplate;

    public CurrencyController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @PostConstruct
    @PreAuthorize("hasRole('Admin')")
    @GetMapping("/currency")
    public List<Currency> getCurrency() {
        Currency[] response = restTemplate.getForObject(PRIVATBANK_API_URL, Currency[].class);
        List<Currency> currencyList = Arrays.asList(response);
        currencyRepository.saveAll(currencyList);
        return currencyList;
    }

}