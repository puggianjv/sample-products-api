package br.com.puggian.products.api.dto.output;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    private Long id;
    private String name;
    private Long quantity;
    private BigDecimal price;
    private LocalDateTime creation;
    private LocalDateTime lastUpdate;
    private SupplierDto supplier;

}
