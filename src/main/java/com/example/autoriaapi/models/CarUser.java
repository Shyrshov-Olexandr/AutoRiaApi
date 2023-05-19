package com.example.autoriaapi.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "cars")
public class CarUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String brand;
    private String model;
    private int price;
    @Column(name = "currency")
    private ECurrency Ecurrency;

    private String region;

    private int view;
    private BigDecimal nationalPrice;
    private BigDecimal usdPrice;
    private BigDecimal eurPrice;
    @Column(name = "last_view_time")
    private LocalDateTime lastViewTime;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    public CarUser(String brand, String model, int price, ECurrency ECurrency, String region) {
        this.brand = brand;
        this.model = model;
        this.price = price;
        this.Ecurrency = ECurrency;
        this.region = region;
    }

    public CarUser(User user) {
        this.user = user;
    }

    public CarUser() {

    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public int getView() {
        return view;
    }

    public void setView(int view) {
        this.view = view;
    }

    public LocalDateTime getLastViewTime() {
        return lastViewTime;
    }

    public void setLastViewTime(LocalDateTime lastViewTime) {
        this.lastViewTime = lastViewTime;
    }
    public int getViewCountForDay() {
        if (lastViewTime == null || !lastViewTime.toLocalDate().equals(LocalDate.now())) {
            return 0;
        }
        return view;
    }

    public int getViewCountForWeek() {
        if (lastViewTime == null || lastViewTime.isBefore(LocalDateTime.now().minusWeeks(1))) {
            return 0;
        }
        return view;
    }
    public int getViewCountForMonth() {
        if (lastViewTime == null || !lastViewTime.toLocalDate().getMonth().equals(LocalDate.now().getMonth())) {
            return 0;
        }
        return view;
    }
    public int getViews() {
        return this.getView() + this.getViewCountForWeek() + this.getViewCountForMonth();
    }

    public ECurrency getEcurrency() {
        return Ecurrency;
    }

    public void setEcurrency(ECurrency ecurrency) {
        Ecurrency = ecurrency;
    }

    public BigDecimal getNationalPrice() {
        return nationalPrice;
    }

    public void setNationalPrice(BigDecimal nationalPrice) {
        this.nationalPrice = nationalPrice;
    }

    public BigDecimal getUsdPrice() {
        return usdPrice;
    }

    public void setUsdPrice(BigDecimal usdPrice) {
        this.usdPrice = usdPrice;
    }

    public BigDecimal getEurPrice() {
        return eurPrice;
    }

    public void setEurPrice(BigDecimal eurPrice) {
        this.eurPrice = eurPrice;
    }
}