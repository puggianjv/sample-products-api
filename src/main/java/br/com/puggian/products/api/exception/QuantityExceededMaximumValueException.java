package br.com.puggian.products.api.exception;

public class QuantityExceededMaximumValueException extends RuntimeException {

    public QuantityExceededMaximumValueException(String message) {
        super(message);
    }

}
