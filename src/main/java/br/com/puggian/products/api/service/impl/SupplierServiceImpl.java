package br.com.puggian.products.api.service.impl;

import br.com.puggian.products.api.dto.input.CreateSupplierDto;
import br.com.puggian.products.api.exception.ResourceNotFoundException;
import br.com.puggian.products.api.model.Supplier;
import br.com.puggian.products.api.repository.SupplierRepository;
import br.com.puggian.products.api.service.SupplierService;
import org.springframework.stereotype.Service;

@Service
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;

    public SupplierServiceImpl(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    @Override
    public Supplier getSupplierById(long id) {
        return supplierRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Supplier not found for id " + id));
    }

    @Override
    public Supplier createSupplier(CreateSupplierDto dto) {
        Supplier supplier = new Supplier();
        supplier.setName(dto.getName());
        return supplierRepository.save(supplier);
    }
}
