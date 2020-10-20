package br.com.puggian.products.api.controller;

import br.com.puggian.products.api.dto.input.CreateSupplierDto;
import br.com.puggian.products.api.dto.output.SupplierDto;
import br.com.puggian.products.api.mapper.SupplierMapper;
import br.com.puggian.products.api.service.SupplierService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value="/suppliers")
public class SupplierController {

    private final SupplierService supplierService;
    private final SupplierMapper mapper;

    public SupplierController(SupplierService supplierService, SupplierMapper mapper){
        this.supplierService = supplierService;
        this.mapper = mapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SupplierDto insertSupplier(@Valid @RequestBody CreateSupplierDto dto) {
        return mapper.convertSupplierToDto(supplierService.createSupplier(dto));
    }

}
