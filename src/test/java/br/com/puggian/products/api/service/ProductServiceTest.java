package br.com.puggian.products.api.service;

import br.com.puggian.products.api.exception.ResourceNotFoundException;
import br.com.puggian.products.api.model.Product;
import br.com.puggian.products.api.model.Supplier;
import br.com.puggian.products.api.repository.ProductRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {

    private ProductService service;

    @Mock
    private ProductRepository repository;

    @Before
    public void setup() {
        service = new ProductServiceImpl(repository);
    }

    @Test
    public void shouldReturnProductList_whenFindProducts() {
        LocalDateTime dateTime = LocalDateTime.now();
        Supplier supplier = new Supplier(1L, "Extra", dateTime, dateTime, null);
        Product product1 = new Product(1L, "Trakinas", 10L, BigDecimal.TEN, dateTime, dateTime, supplier);
        Product product2 = new Product(2L, "Doritos", 10L, BigDecimal.TEN, dateTime, dateTime, supplier);
        List<Product> products = Arrays.asList(product1, product2);
        supplier.setProducts(products);

        when(repository.findAll()).thenReturn(products);

        List<Product> list = service.listProducts();

        assertEquals(2, list.size());
        assertEquals(new Long(1L), list.get(0).getId());
        assertEquals("Trakinas", list.get(0).getName());
        assertEquals(new Long(2L), list.get(1).getId());
    }

    @Test
    public void shouldReturnEmptyList_whenNoProductIsFound() {
        when(repository.findAll()).thenReturn(Collections.emptyList());
        List<Product> list = service.listProducts();
        assertEquals(0, list.size());
    }

    @Test
    public void shouldReturnProduct_whenFindProduct() {
        LocalDateTime dateTime = LocalDateTime.now();
        Supplier supplier = new Supplier(1L, "Extra", dateTime, dateTime, null);
        Product product = new Product(1L, "Trakinas", 10L, BigDecimal.TEN, dateTime, dateTime, supplier);
        List<Product> products = Collections.singletonList(product);
        supplier.setProducts(products);

        when(repository.findById(1L)).thenReturn(Optional.of(product));

        Product result = service.getProductById(1L);

        assertEquals(new Long(1L), result.getId());
        assertEquals("Trakinas", result.getName());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void shouldThrowResourceNotFoundException_whenNoProductIsFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());
        service.getProductById(1L);
    }
}