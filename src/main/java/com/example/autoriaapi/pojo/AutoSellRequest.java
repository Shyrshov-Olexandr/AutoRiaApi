package com.example.autoriaapi.pojo;

import com.example.autoriaapi.models.ECurrency;

public class AutoSellRequest {
    private String brand;
    private String model;
    private int price;
    private ECurrency ECurrency;
    private String owner;
    private String region;

    private int view;


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

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
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

    public com.example.autoriaapi.models.ECurrency getECurrency() {
        return ECurrency;
    }

    public void setECurrency(com.example.autoriaapi.models.ECurrency ECurrency) {
        this.ECurrency = ECurrency;
    }

//    public User getUser() {
//        return user;
//    }
//
//    public void setUser(User user) {
//        this.user = user;
//    }

//    https://api.privatbank.ua/p24api/pubinfo?exchange&json&coursid=11
}