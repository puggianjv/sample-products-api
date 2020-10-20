package br.com.puggian.products.api.dto.output;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ProductDto {

    private Long id;
    private String name;
    private Long quantity;
    private BigDecimal price;
    private LocalDateTime creation;
    private LocalDateTime lastUpdate;
    private SupplierDto supplier;

}
