package com.mandacarubroker.domain.stock;

import jakarta.persistence.Table;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Table(name = "stock")
@Entity(name = "stock")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Stock {

    /**
     * The unique identifier for the stock.
     *
     * This field is annotated with {@code @Id} and {@code @GeneratedValue} to
     * indicate that it represents the primary key of the stock entity. The
     * {@code GenerationType.UUID} strategy is used to generate a unique UUID as
     * the identifier.
     */
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    /**
     * The unique symbol associated with the stock.
     */
    private String symbol;
    /**
     * The name of the company associated with the stock.
     */
    private String companyName;
    /**
     * The current price of the stock.
     */
    private double price;

    /**
     * Constructs a new Stock object based on the provided RequestStockDTO.
     *
     * This constructor initializes a new Stock object. It extracts the symbol, company name,
     * and price from the RequestStockDTO and sets the corresponding attributes of the Stock.
     *
     * @param requestStockDTO The RequestStockDTO containing data for initializing the Stock.
     */
    public Stock(final RequestStockDTO requestStockDTO) {
        this.symbol = requestStockDTO.symbol();
        this.companyName = requestStockDTO.companyName();
        this.price = requestStockDTO.price();
    }


}
