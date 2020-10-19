package br.com.puggian.products.api.dto.output;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SupplierDto {

    private Long id;
    private String name;
    private LocalDateTime creation;
    private LocalDateTime lastUpdate;

}
