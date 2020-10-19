package br.com.puggian.products.api.dto.input;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductQuantityDto {

    @NotNull(message = "{product.quantity.notNull}")
    private long quantity;

}
