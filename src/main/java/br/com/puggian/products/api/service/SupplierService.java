package br.com.puggian.products.api.service;

import br.com.puggian.products.api.dto.input.CreateSupplierDto;
import br.com.puggian.products.api.model.Supplier;

public interface SupplierService {

    Supplier getSupplierById(long id);

    Supplier createSupplier(CreateSupplierDto dto);
}
