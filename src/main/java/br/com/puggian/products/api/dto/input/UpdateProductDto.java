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
public class UpdateProductDto {

    @NotBlank(message = "{product.name.notBlank}")
    private String name;

    @DecimalMin(value = "0.01", message = "{product.price.decimalMin}")
    @Digits(integer=999999999, fraction=2,
            message = "{product.price.digits}")
    @NotNull(message = "{product.price.notNull}")
    private BigDecimal price;

}
