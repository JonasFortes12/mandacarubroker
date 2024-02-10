package com.mandacarubroker.controller;


import com.mandacarubroker.domain.stock.RequestStockDTO;
import com.mandacarubroker.domain.stock.Stock;
import com.mandacarubroker.domain.stock.StockRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest
class StockControllerTest {

    @Autowired
    private MockMvc mockMvc;

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
    void itShouldGetAllStocks() throws Exception {

        RequestBuilder request = MockMvcRequestBuilders.get("/stocks");

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3));
    }


    @Test
    void itShouldRetrieveStockById() throws Exception {

        Stock targetStock = stockRepository.findAll().get(0);

        RequestBuilder request = MockMvcRequestBuilders.get("/stocks/{id}",targetStock.getId());

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.symbol").value(targetStock.getSymbol()))
                .andExpect(jsonPath("$.companyName").value(targetStock.getCompanyName()))
                .andExpect(jsonPath("$.price").value(targetStock.getPrice()));
    }



}

