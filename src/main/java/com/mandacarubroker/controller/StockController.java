package com.mandacarubroker.controller;

import com.mandacarubroker.domain.stock.Stock;
import com.mandacarubroker.domain.stock.RequestStockDTO;
import com.mandacarubroker.service.StockService;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;


import java.util.List;


@RestController
@RequestMapping("/stocks")
public class StockController {


    /**
     * The StockService instance used for interacting with stock-related operations.
     * The StockService provides methods for managing and retrieving stock-related information.
     */
    private final StockService stockService;

    /**
     * Constructs a StockController with the specified StockService.
     * This constructor initializes a StockController with the provided StockService.
     *
     * @param stockService The StockService instance used by the controller for stock-related operations.
     */
    public StockController(final StockService stockService) {
        this.stockService = stockService;
    }

    /**
     * Returns a list of all available stocks.
     * This method uses the HTTP GET operation to retrieve all stocks
     * from the associated service and returns them as a list. Each element
     * in the list represents a stock entity.
     *
     * @return A list containing all available stocks.
     */
    @GetMapping
    public List<Stock> getAllStocks() {
        return stockService.getAllStocks();
    }

    /**
     * Retrieves a stock by its unique identifier.
     * This method uses the HTTP GET operation with a path variable to obtain
     * a specific stock from the associated service based on its ID.
     * The ID is provided as a path variable in the URL.
     *
     * @param id The unique identifier of the stock to be retrieved.
     * @return The stock with the specified ID if found, or null if not found.
     */
    @GetMapping("/{id}")
    public Stock getStockById(final @PathVariable String id) {
        return stockService.getStockById(id).orElse(null);
    }

    /**
     * Creates a new stock based on the provided data.
     * This method uses the HTTP POST operation to create a new stock
     * by processing the data provided in the request body. The information
     * required to create the stock is encapsulated
     * in the RequestStockDTO object (Data Transfer Object).
     *
     * @param data The data necessary to create the new stock,
     *             encapsulated in a RequestStockDTO.
     * @return A ResponseEntity containing the created stock
     * and an HTTP status of 200 (OK).
     */
    @PostMapping
    public ResponseEntity<Stock> createStock(final @RequestBody RequestStockDTO data) {
        Stock createdStock = stockService.createStock(data);
        return ResponseEntity.ok(createdStock);
    }

    /**
     * Updates an existing stock with the provided data.
     * This method uses the HTTP PUT operation to update an existing stock
     * based on the provided data in the request body. The ID of the stock
     * to be updated is specified as a path variable in the URL.
     *
     * @param id The unique identifier of the stock to be updated,
     *           provided as a path variable.
     * @param updatedStock The updated stock data provided in the request body.
     * @return The updated stock if the operation is successful,
     * or null if the stock with the specified ID is not found.
     */
    @PutMapping("/{id}")
    public Stock updateStock(final @PathVariable String id, final @RequestBody Stock updatedStock) {
        return stockService.updateStock(id, updatedStock).orElse(null);
    }

    /**
     * Deletes a stock with the specified ID.
     * This method uses the HTTP DELETE operation to delete a stock based on the
     * unique identifier provided as a path variable in the URL.
     *
     * @param id The unique identifier of the stock to be deleted,
     *           provided as a path variable.
     */
    @DeleteMapping("/{id}")
    public void deleteStock(final @PathVariable String id) {
        stockService.deleteStock(id);
    }

}
