package br.com.puggian.products.api.controller;

import br.com.puggian.products.api.dto.output.ApiErrorDto;
import br.com.puggian.products.api.exception.QuantityExceededMaximumValueException;
import br.com.puggian.products.api.exception.QuantityNotAvailableException;
import br.com.puggian.products.api.exception.ResourceNotFoundException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ApiErrorDto handleResourceNotFoundException(ResourceNotFoundException ex) {
        return new ApiErrorDto(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ QuantityNotAvailableException.class, QuantityExceededMaximumValueException.class })
    public ApiErrorDto handleQuantityException(RuntimeException ex) {
        return new ApiErrorDto(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiErrorDto handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        return new ApiErrorDto("Input validation failed", errors);
    }

}
