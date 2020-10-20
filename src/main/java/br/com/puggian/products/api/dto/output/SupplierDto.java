package br.com.puggian.products.api.dto.output;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class SupplierDto {

    private Long id;
    private String name;
    private LocalDateTime creation;
    private LocalDateTime lastUpdate;

}
