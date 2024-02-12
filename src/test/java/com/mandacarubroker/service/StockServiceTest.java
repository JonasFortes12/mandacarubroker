package com.mandacarubroker.service;

import com.mandacarubroker.domain.stock.RequestStockDTO;
import com.mandacarubroker.domain.stock.Stock;
import com.mandacarubroker.domain.stock.StockRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
class StockServiceTest {

    @Autowired
    private StockService stockService;

    @Autowired
    private StockRepository stockRepository;

    @BeforeEach
    public void initRepository() {
        stockRepository.save(new Stock(new RequestStockDTO("RPM3", "3R PETROLEUM", 90.45)));
        stockRepository.save(new Stock(new RequestStockDTO("ALL3", "ALLOS", 121.60)));
        stockRepository.save(new Stock(new RequestStockDTO("AZL4", "AZUL", 230.20)));
    }

    @AfterEach
    public void clearRepository() {
        stockRepository.deleteAll();
    }


    
    @Test
    void itShouldGetAllStocks() {

        List<Stock> expectedStocks = List.of(
            new Stock(new RequestStockDTO("RPM3", "3R PETROLEUM", 90.45)),
            new Stock(new RequestStockDTO("ALL3", "ALLOS", 121.60)),
            new Stock(new RequestStockDTO("AZL4", "AZUL", 230.20))
        );

        List<Stock> retrievesStocks = stockService.getAllStocks();

        for (int i = 0; i < retrievesStocks.size(); i++){
            assertEquals(retrievesStocks.get(i).getSymbol(), expectedStocks.get(i).getSymbol());
            assertEquals(retrievesStocks.get(i).getCompanyName(), expectedStocks.get(i).getCompanyName());
            assertEquals(retrievesStocks.get(i).getPrice(), expectedStocks.get(i).getPrice());
        }


    }





}