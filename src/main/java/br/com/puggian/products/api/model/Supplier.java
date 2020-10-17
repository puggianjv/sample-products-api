package br.com.puggian.products.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Supplier {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private LocalDateTime creation;
    private LocalDateTime lastUpdate;

    @OneToMany(mappedBy="supplier", fetch = FetchType.LAZY)
    private List<Product> products;

}
