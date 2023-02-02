package com.udteam.miristock.service;


import com.udteam.miristock.dto.StockDataRequestDto;
import com.udteam.miristock.dto.StockDataResponseDto;
import com.udteam.miristock.entity.StockDataEntity;
import com.udteam.miristock.repository.StockDataCustomRepository;
import com.udteam.miristock.repository.StockDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StockDataService {

    private final StockDataRepository stockDataRepository;
    private final StockDataCustomRepository stockDataCustomRepository;

    // 거래량 가장 많은 5개 출력
    public List<StockDataResponseDto> findTop5AmountDesc(Integer stockDataDate) {
        return stockDataRepository.findTopByStockCodeOrderByStockDataAmountDesc(stockDataDate)
        .stream().map(StockDataResponseDto::new)
        .collect(Collectors.toList());
    }

    // 등락률 가장 낮은 5개 순서대로 가져오기
    public List<StockDataResponseDto> findTop5BySFlucauationRateAsc(Integer stockDataDate){
        return stockDataRepository.findTop5ByStockDataDateOrderByStockDataFlucauationRateAsc(stockDataDate)
        .stream().map(StockDataResponseDto::new)
        .collect(Collectors.toList());
    }

    // 등락률 가장 높은 순서대로 5개 출력
    public List<StockDataResponseDto> findTop5BySFlucauationRateDesc(Integer stockDataDate){
        return stockDataRepository.findTop5ByStockDataDateOrderByStockDataFlucauationRateDesc(stockDataDate)
        .stream().map(StockDataResponseDto::new)
        .collect(Collectors.toList());
    }

    public List<StockDataResponseDto> findStockData(Integer searchStartDate, Integer searchEndDate, String searchStockCode){
        return stockDataCustomRepository.findStockData(searchStartDate, searchEndDate, searchStockCode)
                .stream().map(StockDataResponseDto::new)
                .collect(Collectors.toList());
    }

}
