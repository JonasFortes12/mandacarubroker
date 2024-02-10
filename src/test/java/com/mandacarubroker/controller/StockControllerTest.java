package com.mandacarubroker.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.mandacarubroker.domain.stock.RequestStockDTO;
import com.mandacarubroker.domain.stock.Stock;
import com.mandacarubroker.domain.stock.StockRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
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

    @Autowired
    private ObjectMapper objectMapper;

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


    @Test
    void itShouldReturnNullAtGetNonexistentStockById() throws Exception {
        String nonexistentId = "1a2b3c2d";
        RequestBuilder request = MockMvcRequestBuilders.get("/stocks/{id}", nonexistentId);

        mockMvc.perform(request)
                .andExpect(status().isNotFound());
    }


    @Test
    void itShouldCreateNewStock() throws Exception {
        RequestStockDTO newStock = new RequestStockDTO("CMG4", "CEMIG", 129.67);

        String requestJson = objectMapper.writeValueAsString(newStock);

        RequestBuilder request = MockMvcRequestBuilders
                .post("/stocks")
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.symbol").value(newStock.symbol()))
                .andExpect(jsonPath("$.companyName").value(newStock.companyName()))
                .andExpect(jsonPath("$.price").value(newStock.price()));

    }


    @Test
    void itShouldNotCreateNewStockWithExistentSymbol() throws Exception {
        RequestStockDTO newStock = new RequestStockDTO("RPM3", "PETROLEUM", 134.67);

        String requestJson = objectMapper.writeValueAsString(newStock);

        RequestBuilder request = MockMvcRequestBuilders
                .post("/stocks")
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isConflict());
    }


}

