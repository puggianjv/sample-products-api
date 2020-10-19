package br.com.puggian.products.api.dto;

import lombok.Data;

import java.util.Collections;
import java.util.List;

@Data
public class ApiErrorDto {

    private String message;
    private List<String> errors;

    public ApiErrorDto(String message, List<String> errors) {
        this.message = message;
        this.errors = errors;
    }

    public ApiErrorDto(String message) {
        this.message = message;
        this.errors = Collections.emptyList();
    }
}
