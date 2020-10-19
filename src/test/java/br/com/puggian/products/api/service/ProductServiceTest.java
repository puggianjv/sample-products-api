package br.com.puggian.products.api.service;

import br.com.puggian.products.api.dto.input.CreateProductDto;
import br.com.puggian.products.api.dto.input.ProductQuantityDto;
import br.com.puggian.products.api.dto.input.UpdateProductDto;
import br.com.puggian.products.api.exception.QuantityExceededMaximumValueException;
import br.com.puggian.products.api.exception.QuantityNotAvailableException;
import br.com.puggian.products.api.exception.ResourceNotFoundException;
import br.com.puggian.products.api.model.Product;
import br.com.puggian.products.api.model.Supplier;
import br.com.puggian.products.api.repository.ProductRepository;
import br.com.puggian.products.api.service.impl.ProductServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.dao.EmptyResultDataAccessException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {

    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private SupplierService supplierService;

    @Before
    public void setup() {
        productService = new ProductServiceImpl(productRepository, supplierService);
    }

    @Test
    public void listProducts_ProductsFound_List() {
        LocalDateTime dateTime = LocalDateTime.now();
        Supplier supplier = new Supplier(1L, "Extra", dateTime, dateTime, null);
        Product product1 = new Product(1L, "Trakinas", 10L, BigDecimal.TEN, dateTime, dateTime, supplier);
        Product product2 = new Product(2L, "Doritos", 10L, BigDecimal.TEN, dateTime, dateTime, supplier);
        List<Product> products = Arrays.asList(product1, product2);
        supplier.setProducts(products);

        when(productRepository.findAll()).thenReturn(products);

        List<Product> list = productService.listProducts();

        assertEquals(2, list.size());
        assertEquals(new Long(1L), list.get(0).getId());
        assertEquals("Trakinas", list.get(0).getName());
        assertEquals(new Long(2L), list.get(1).getId());
    }

    @Test
    public void listProducts_NoProductFound_EmptyList() {
        when(productRepository.findAll()).thenReturn(Collections.emptyList());
        List<Product> list = productService.listProducts();
        assertEquals(0, list.size());
    }

    @Test
    public void getProductById_ProductFound_Product() {
        LocalDateTime dateTime = LocalDateTime.now();
        Supplier supplier = new Supplier(1L, "Extra", dateTime, dateTime, null);
        Product product = new Product(1L, "Trakinas", 10L, BigDecimal.TEN, dateTime, dateTime, supplier);
        List<Product> products = Collections.singletonList(product);
        supplier.setProducts(products);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        Product result = productService.getProductById(1L);

        assertEquals(new Long(1L), result.getId());
        assertEquals("Trakinas", result.getName());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void getProductById_ProductNotFound_ThrowResourceNotFoundException() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());
        productService.getProductById(1L);
    }

    @Test
    public void createProduct_ProductSaved_Product() {
        CreateProductDto dto = new CreateProductDto("Trakinas", BigDecimal.ONE, 1L);
        ArgumentCaptor<Product> captor = ArgumentCaptor.forClass(Product.class);
        LocalDateTime dateTime = LocalDateTime.now();
        Supplier supplier = new Supplier(1L, "Extra", dateTime, dateTime, Collections.emptyList());

        when(supplierService.getSupplierById(1L)).thenReturn(supplier);
        when(productRepository.save(any(Product.class))).thenReturn(new Product());

        productService.createProduct(dto);

        verify(productRepository).save(captor.capture());
        Product product = captor.getValue();
        assertEquals("Trakinas", product.getName());
        assertEquals(new Long(0L), product.getQuantity());
        assertEquals(BigDecimal.ONE, product.getPrice());
        assertEquals(new Long(1L), product.getSupplier().getId());
        assertEquals("Extra", product.getSupplier().getName());
    }

    @Test
    public void updateProduct_ProductUpdated_Product() {
        LocalDateTime dateTime = LocalDateTime.now();
        Supplier supplier = new Supplier(1L, "Extra", dateTime, dateTime, null);
        Product product = new Product(1L, "Trakinas", 0L, BigDecimal.TEN, dateTime, dateTime, supplier);
        List<Product> products = Collections.singletonList(product);
        supplier.setProducts(products);
        UpdateProductDto dto = new UpdateProductDto("Doritos", BigDecimal.TEN);

        ArgumentCaptor<Product> captor = ArgumentCaptor.forClass(Product.class);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(new Product());

        productService.updateProduct(dto, 1L);

        verify(productRepository).save(captor.capture());
        Product productToSave = captor.getValue();
        assertEquals("Doritos", productToSave.getName());
        assertEquals(new Long(0L), productToSave.getQuantity());
        assertEquals(BigDecimal.TEN, productToSave.getPrice());
        assertEquals(new Long(1L), productToSave.getSupplier().getId());
        assertEquals("Extra", productToSave.getSupplier().getName());
    }

    @Test
    public void deleteProduct_ProductDeleted_CallRepositoryWithId() {
        ArgumentCaptor<Long> captor = ArgumentCaptor.forClass(Long.class);

        doNothing().when(productRepository).deleteById(1L);

        productService.deleteProduct(1L);

        verify(productRepository).deleteById(captor.capture());
        assertEquals(new Long(1L), captor.getValue());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void deleteProduct_ProductNotFound_ThrowResourceNotFoundException() {
        ArgumentCaptor<Long> captor = ArgumentCaptor.forClass(Long.class);

        doThrow(new EmptyResultDataAccessException(1)).when(productRepository).deleteById(1L);

        productService.deleteProduct(1L);
    }

    @Test
    public void addQuantity_QuantityUpdatedWithPositiveValue_Product() {
        LocalDateTime dateTime = LocalDateTime.now();
        Supplier supplier = new Supplier(1L, "Extra", dateTime, dateTime, null);
        Product product = new Product(1L, "Trakinas", 0L, BigDecimal.TEN, dateTime, dateTime, supplier);
        List<Product> products = Collections.singletonList(product);
        supplier.setProducts(products);
        ProductQuantityDto dto = new ProductQuantityDto(1L);

        ArgumentCaptor<Product> captor = ArgumentCaptor.forClass(Product.class);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(new Product());

        productService.addQuantity(dto, 1L);

        verify(productRepository).save(captor.capture());
        Product productToSave = captor.getValue();
        assertEquals("Trakinas", productToSave.getName());
        assertEquals(new Long(1L), productToSave.getQuantity());
        assertEquals(BigDecimal.TEN, productToSave.getPrice());
        assertEquals(new Long(1L), productToSave.getSupplier().getId());
        assertEquals("Extra", productToSave.getSupplier().getName());
    }

    @Test
    public void addQuantity_QuantityUpdatedWithNegativeValue_Product() {
        LocalDateTime dateTime = LocalDateTime.now();
        Supplier supplier = new Supplier(1L, "Extra", dateTime, dateTime, null);
        Product product = new Product(1L, "Trakinas", 1L, BigDecimal.TEN, dateTime, dateTime, supplier);
        List<Product> products = Collections.singletonList(product);
        supplier.setProducts(products);
        ProductQuantityDto dto = new ProductQuantityDto(-1L);

        ArgumentCaptor<Product> captor = ArgumentCaptor.forClass(Product.class);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(new Product());

        productService.addQuantity(dto, 1L);

        verify(productRepository).save(captor.capture());
        Product productToSave = captor.getValue();
        assertEquals("Trakinas", productToSave.getName());
        assertEquals(new Long(0L), productToSave.getQuantity());
        assertEquals(BigDecimal.TEN, productToSave.getPrice());
        assertEquals(new Long(1L), productToSave.getSupplier().getId());
        assertEquals("Extra", productToSave.getSupplier().getName());
    }

    @Test(expected = QuantityNotAvailableException.class)
    public void addQuantity_QuantityUpdatedWithNegativeValueAndStockHasNotEnoughItems_ThrowQuantityNotAvailableException() {
        LocalDateTime dateTime = LocalDateTime.now();
        Supplier supplier = new Supplier(1L, "Extra", dateTime, dateTime, null);
        Product product = new Product(1L, "Trakinas", 0L, BigDecimal.TEN, dateTime, dateTime, supplier);
        List<Product> products = Collections.singletonList(product);
        supplier.setProducts(products);
        ProductQuantityDto dto = new ProductQuantityDto(-1L);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        productService.addQuantity(dto, 1L);
    }

    @Test(expected = QuantityExceededMaximumValueException.class)
    public void addQuantity_QuantityUpdatedWithValueThatExceedsMaximumQuantity_ThrowQuantityExceededMaximumValueException() {
        LocalDateTime dateTime = LocalDateTime.now();
        Supplier supplier = new Supplier(1L, "Extra", dateTime, dateTime, null);
        Product product = new Product(1L, "Trakinas", 0L, BigDecimal.TEN, dateTime, dateTime, supplier);
        List<Product> products = Collections.singletonList(product);
        supplier.setProducts(products);
        ProductQuantityDto dto = new ProductQuantityDto(999999999L + 1L);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        productService.addQuantity(dto, 1L);
    }

    @Test(expected = QuantityExceededMaximumValueException.class)
    public void addQuantity_QuantityUpdatedWithValueThatExceedsMaximumQuantityWhenSummedWithStock_ThrowQuantityExceededMaximumValueException() {
        LocalDateTime dateTime = LocalDateTime.now();
        Supplier supplier = new Supplier(1L, "Extra", dateTime, dateTime, null);
        Product product = new Product(1L, "Trakinas", 1L, BigDecimal.TEN, dateTime, dateTime, supplier);
        List<Product> products = Collections.singletonList(product);
        supplier.setProducts(products);
        ProductQuantityDto dto = new ProductQuantityDto(999999999L);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        productService.addQuantity(dto, 1L);
    }

}