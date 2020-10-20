package br.com.puggian.products.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
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

    @PrePersist
    public void onPrePersist() {
        LocalDateTime now = LocalDateTime.now();
        this.setCreation(now);
        this.setLastUpdate(now);
    }

    @PreUpdate
    public void onPreUpdate() {
        LocalDateTime now = LocalDateTime.now();
        this.setLastUpdate(now);
    }

}
