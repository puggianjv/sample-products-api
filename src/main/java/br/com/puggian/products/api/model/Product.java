package br.com.puggian.products.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Where(clause = "deleted = false")
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

    @Column(columnDefinition = "boolean default false")
    private boolean deleted;

    public void setDeleted() {
        this.deleted = true;
    }

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
