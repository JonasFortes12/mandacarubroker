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

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
class StockServiceTest {

    @Autowired
    private StockService stockService;

    @Autowired
    private StockRepository stockRepository;


    private void saveStock(Stock stock) {
        stockRepository.save(stock);
    }


    @Test
    void itShouldGetAllStocks() {
        Stock stock01 = new Stock(new RequestStockDTO("RPM3", "3R PETROLEUM", 90.45));
        Stock stock02 = new Stock(new RequestStockDTO("ALL3", "ALLOS", 121.60));

        saveStock(stock01);
        saveStock(stock02);

        List<Stock> retrievesStocks = stockService.getAllStocks();

        assertEquals(retrievesStocks.get(0).getSymbol(), stock01.getSymbol());
        assertEquals(retrievesStocks.get(1).getSymbol(), stock02.getSymbol());

        assertEquals(retrievesStocks.get(0).getCompanyName(), stock01.getCompanyName());
        assertEquals(retrievesStocks.get(1).getCompanyName(), stock02.getCompanyName());

        assertEquals(retrievesStocks.get(0).getPrice(), stock01.getPrice());
        assertEquals(retrievesStocks.get(1).getPrice(), stock02.getPrice());

    }







}