package br.com.puggian.products.api.service;

import br.com.puggian.products.api.dto.input.CreateProductDto;
import br.com.puggian.products.api.dto.input.CreateSupplierDto;
import br.com.puggian.products.api.exception.ResourceNotFoundException;
import br.com.puggian.products.api.model.Product;
import br.com.puggian.products.api.model.Supplier;
import br.com.puggian.products.api.repository.SupplierRepository;
import br.com.puggian.products.api.service.impl.SupplierServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SupplierServiceTest {

    private SupplierService supplierService;

    @Mock
    private SupplierRepository supplierRepository;

    @Before
    public void setup() {
        supplierService = new SupplierServiceImpl(supplierRepository);
    }

    @Test
    public void getSupplierById_SupplierFound_Supplier() {
        LocalDateTime dateTime = LocalDateTime.now();
        Supplier supplier = new Supplier(1L, "Extra", Collections.emptyList(), dateTime, dateTime);

        when(supplierRepository.findById(1L)).thenReturn(Optional.of(supplier));

        Supplier result = supplierService.getSupplierById(1L);

        assertEquals(new Long(1L), result.getId());
        assertEquals("Extra", result.getName());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void getSupplierById_SupplierNotFound_ThrowResourceNotFoundException() {
        when(supplierRepository.findById(1L)).thenReturn(Optional.empty());
        supplierService.getSupplierById(1L);
    }

    @Test
    public void createProduct_SupplierSaved_Supplier() {
        ArgumentCaptor<Supplier> captor = ArgumentCaptor.forClass(Supplier.class);
        LocalDateTime dateTime = LocalDateTime.now();
        CreateSupplierDto dto = new CreateSupplierDto("Extra");

        when(supplierRepository.save(any(Supplier.class))).thenReturn(new Supplier());

        supplierService.createSupplier(dto);

        verify(supplierRepository).save(captor.capture());
        Supplier result = captor.getValue();
        assertEquals("Extra", result.getName());
    }
}