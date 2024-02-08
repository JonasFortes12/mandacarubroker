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

@Table(name ="stock")
@Entity(name="stock")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of="id")
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
     * Additionally, it calculates the new price using the changePrice method.
     *
     * @param requestStockDTO The RequestStockDTO containing data for initializing the Stock.
     */
    public Stock(final RequestStockDTO requestStockDTO){
        this.symbol = requestStockDTO.symbol();
        this.companyName = requestStockDTO.companyName();
        this.price = changePrice(requestStockDTO.price(), true);
    }

    /**
     * Changes the price of the stock based on the specified amount and direction.
     *
     * This method adjusts the stock price according to the specified amount and
     * direction (increase or decrease). If the direction is set to increase and
     * the specified amount is less than the current price, it invokes the
     * {@code increasePrice} method; otherwise, it invokes the {@code decreasePrice}
     * method. If the direction is set to decrease and the specified amount is
     * greater than the current price, it invokes the {@code increasePrice} method;
     * otherwise, it invokes the {@code decreasePrice} method.
     *
     * @param amount The amount by which to adjust the stock price.
     * @param increase A boolean indicating whether to increase (true)
     *                 or decrease (false) the price.
     * @return The adjusted stock price.
     */
    public double changePrice(final double amount, final boolean increase) {
        if (increase) {
            if (amount < this.price) {
                return increasePrice(amount);
            } else {
                return decreasePrice(amount);
            }
        } else {
            if (amount > this.price) {
                return increasePrice(amount);
            } else {
                return this.decreasePrice(amount);
            }
        }
    }

    /**
     * Increases the stock price by the specified amount.
     *
     * @param amount The amount by which to increase the stock price.
     * @return The updated stock price after the increase.
     */
    public double increasePrice(final double amount) {
        return this.price + amount;
    }

    /**
     * Decreases the stock price by the specified amount.
     *
     * @param amount The amount by which to decrease the stock price.
     * @return The updated stock price after the decrease.
     */
    public double decreasePrice(final double amount) {
        return this.price - amount;
    }

}