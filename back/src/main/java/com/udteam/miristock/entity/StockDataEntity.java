package com.udteam.miristock.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "stockdata")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class StockDataEntity {

    @Id
    @Column(name = "stockdata_no")
    private Integer stockDataNo;

    @Column(name = "stock_code")
    private String stockCode;

    @Column(name = "stock_name")
    private String stockName;

    @Column(name = "stockdata_date")
    private Integer stockDataDate;

    @Column(name = "stockdata_closing_price")
    private Integer stockDataClosingPrice;

    @Column(name = "stockdata_amount")
    private Long stockDataAmount;

    @Column(name = "stockdata_price_increasement")
    private Integer stockDataPriceIncreasement;

    @Column(name = "stockdata_flucauation_rate")
    private Float stockDataFlucauationRate;

}
