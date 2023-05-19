package com.example.autoriaapi.pojo;

import javax.persistence.*;
import java.math.BigDecimal;
@Entity
public class Currency  {

    //    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id", nullable = false, updatable = false)
//    private long id;
    @Id
    @Column(length = 20)

    private String ccy;
    @Column(length = 20)

    private String baseCcy = "UAH";
    @Column(length = 20)

    private BigDecimal buy;
    @Column(length = 20)

    private BigDecimal sale;

    public String getCcy() {
        return ccy;
    }

    public void setCcy(String ccy) {
        this.ccy = ccy;
    }

    public String getBaseCcy() {
        return baseCcy;
    }

    public void setBaseCcy(String baseCcy) {
        this.baseCcy = baseCcy;
    }

    public BigDecimal getBuy() {
        return buy;
    }

    public void setBuy(BigDecimal buy) {
        this.buy = buy;
    }

    public BigDecimal getSale() {
        return sale;
    }

    public void setSale(BigDecimal sale) {
        this.sale = sale;
    }

    public Currency(String ccy, String baseCcy, BigDecimal buy, BigDecimal sale) {
        this.ccy = ccy;
        this.baseCcy = baseCcy;
        this.buy = buy;
        this.sale = sale;
    }

    public Currency() {
    }
}