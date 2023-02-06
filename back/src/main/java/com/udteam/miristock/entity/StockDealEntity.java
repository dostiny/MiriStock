package com.udteam.miristock.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name ="stockdeal")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class StockDealEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stockdeal_no")
    private Integer stockDealNo;

    @Column(name = "stock_code")
    private String stockCode;

    @Column(name = "stock_name")
    private String stockName;

    @Column(name = "member_no")
    private Integer memberNo;

    @Column(name = "stockdeal_date")
    private Integer stockDealDate;

    @Column(name = "stockdeal_buy_closing_price")
    private Integer stockDealBuyClosingPrice;

    @Column(name = "stockdeal_sell_closing_price")
    private Integer stockDealSellClosingPrice;

    @Column(name = "stockdeal_amount")
    private Integer stockDealAmount;

    @Column(name = "stockdeal_type")
    @Enumerated(EnumType.STRING)
    private Deal stockDealType;

    @Column(name = "stockdeal_earn_rate")
    private Float stockDealEarnRate;

    @Column(name = "stockdeal_earn_price")
    private Long stockDealEarnPrice;
}
