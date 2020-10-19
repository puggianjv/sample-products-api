package br.com.puggian.products.api.repository;

import br.com.puggian.products.api.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

@org.springframework.stereotype.Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {

}
