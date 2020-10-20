package br.com.puggian.products.api.dto.input;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateSupplierDto {

    @NotBlank(message = "{supplier.name.notBlank}")
    private String name;

}
