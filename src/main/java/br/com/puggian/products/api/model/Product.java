package br.com.puggian.products.api.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Product {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Long quantity;
    private BigDecimal price;
    private LocalDateTime creation;
    private LocalDateTime lastUpdate;

    @ManyToOne(fetch = FetchType.LAZY)
    private Supplier supplier;

}
