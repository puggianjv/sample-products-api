package br.com.puggian.products.api.dto.input;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductDto {

    @NotBlank(message = "Name is required")
    private String name;

    @DecimalMin(value = "0.01", message = "Minimum price: 0.01")
    @Digits(integer=999999999, fraction=2,
            message = "Invalid price. It needs to be a number between 0.01 and 999999999.00")
    @NotNull(message = "Price is required")
    private BigDecimal price;

    @NotNull(message = "Supplier id is required")
    private Long supplierId;

}
