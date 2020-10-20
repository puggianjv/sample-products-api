package br.com.puggian.products.api.dto.input;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductQuantityDto {

    @NotNull(message = "{product.quantity.notNull}")
    private long quantity;

}
