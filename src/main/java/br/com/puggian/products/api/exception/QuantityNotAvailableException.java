package br.com.puggian.products.api.exception;

public class QuantityNotAvailableException extends RuntimeException {

    public QuantityNotAvailableException(String message) {
        super(message);
    }

}
