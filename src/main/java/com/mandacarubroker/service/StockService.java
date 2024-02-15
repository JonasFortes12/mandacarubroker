package com.mandacarubroker.service;

import com.mandacarubroker.domain.stock.RequestStockDTO;
import com.mandacarubroker.domain.stock.Stock;
import com.mandacarubroker.domain.stock.StockRepository;

import jakarta.validation.ValidatorFactory;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class StockService {

    /**
     * The repository responsible for data access and manipulation of stock entities.
     */
    private final StockRepository stockRepository;

    /**
     * Constructs a new instance of the {@link StockService}.
     *
     * @param stockRepository The repository for stock entities. Must not be null.
     */
    public StockService(final StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    /**
     * Retrieves a list of all available stocks.
     *
     * This method delegates the retrieval of stock entities to the associated
     * {@link StockRepository} by invoking its {@code findAll} method. The returned
     * list represents all stocks present in the underlying data storage.
     *
     * @return A list containing all available stocks.
     */
    public List<Stock> getAllStocks() {
        return stockRepository.findAll();
    }

    /**
     * Retrieves a stock by its unique identifier.
     *
     * This method delegates the retrieval of a specific stock entity to the associated
     * {@link StockRepository} by invoking its {@code findById} method with the provided ID.
     *
     * @param id The unique identifier of the stock to be retrieved.
     * @return An {@link Optional} containing the stock with the specified ID if found,
     *         or an empty {@link Optional} if the stock is not found.
     */
    public Optional<Stock> getStockById(final String id) {
        return stockRepository.findById(id);
    }

    /**
     * Creates a new stock based on the provided data.
     *
     * This method instantiates a new {@link Stock} object using the data from the
     * provided {@link RequestStockDTO}. It then validates the data using the
     * {@code validateRequestStockDTO} method and persists the new stock entity
     * to the associated {@link StockRepository} using the {@code save} method.
     *
     * @param data The data representing the new stock to be created.
     * @return The created stock entity.
     * @throws ConstraintViolationException If the provided data is not valid.
     */
    public Stock createStock(final RequestStockDTO data) {
        Stock newStock = new Stock(data);
        validateRequestStockDTO(data);
        return stockRepository.save(newStock);
    }

    /**
     * Updates an existing stock with the provided data.
     *
     * This method retrieves the existing stock entity from the associated
     * {@link StockRepository} using the provided ID. If the stock is found, it
     * updates its attributes with the data from the provided {@link Stock} object.
     * Persists the updated stock entity back to the repository
     * using the {@code save} method.
     *
     * @param id The unique identifier of the stock to be updated.
     * @param updatedStock The data representing the updated stock.
     * @return An {@link Optional} containing the updated stock entity if found,
     *         or an empty {@link Optional} if the stock with the specified ID is not found.
     */
    public Optional<Stock> updateStock(final String id, final Stock updatedStock) {
        return stockRepository.findById(id)
                .map(stock -> {
                    stock.setSymbol(updatedStock.getSymbol());
                    stock.setCompanyName(updatedStock.getCompanyName());
                    stock.setPrice(updatedStock.getPrice());

                    return stockRepository.save(stock);
                });
    }

    /**
     * Deletes a stock by its unique identifier.
     *
     * This method removes the stock entity associated with the specified ID from
     * the underlying data storage by invoking the {@code deleteById} method of the
     * associated {@link StockRepository}.
     *
     * @param id The unique identifier of the stock to be deleted.
     */
    public void deleteStock(final String id) {
        stockRepository.deleteById(id);
    }

    /**
     * Validates a RequestStockDTO object using Bean Validation.
     *
     * This static method uses the Bean Validation API to validate the provided
     * {@link RequestStockDTO} object. It checks for constraints specified by annotations
     * on the fields of the DTO. If validation fails, a {@link ConstraintViolationException}
     * is thrown, providing details about the validation errors.
     *
     * @param data The RequestStockDTO object to be validated.
     * @throws ConstraintViolationException If the validation of the RequestStockDTO fails,
     *                                      containing details of the validation errors.
     */
    public static void validateRequestStockDTO(final RequestStockDTO data) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<RequestStockDTO>> violations = validator.validate(data);

        if (!violations.isEmpty()) {
            StringBuilder errorMessage = new StringBuilder("Validation failed. Details: ");

            for (ConstraintViolation<RequestStockDTO> violation : violations) {
                errorMessage.append(
                        String.format("[%s: %s], ",
                                violation.getPropertyPath(),
                                violation.getMessage()
                        )
                );
            }

            errorMessage.delete(errorMessage.length() - 2, errorMessage.length());

            throw new ConstraintViolationException(errorMessage.toString(), violations);
        }
    }

    /**
     * Validates the RequestStockDTO and creates a new stock if validation succeeds.
     *
     * This method first validates the provided {@link RequestStockDTO} using the
     * {@code validateRequestStockDTO} method. If the validation is successful, a
     * new {@link Stock} object is instantiated using the provided data, and it is
     * then persisted to the associated {@link StockRepository} using the {@code save} method.
     *
     * @param data The RequestStockDTO object containing data for creating a new stock.
     * @throws ConstraintViolationException If the validation of the RequestStockDTO fails,
     *                                      containing details of the validation errors.
     */
    public void validateAndCreateStock(final RequestStockDTO data) {
        validateRequestStockDTO(data);

        Stock newStock = new Stock(data);
        stockRepository.save(newStock);
    }
}
