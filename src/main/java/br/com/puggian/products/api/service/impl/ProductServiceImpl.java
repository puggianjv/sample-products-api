package br.com.puggian.products.api.service.impl;

import br.com.puggian.products.api.dto.input.CreateProductDto;
import br.com.puggian.products.api.dto.input.ProductQuantityDto;
import br.com.puggian.products.api.dto.input.UpdateProductDto;
import br.com.puggian.products.api.exception.QuantityExceededMaximumValueException;
import br.com.puggian.products.api.exception.QuantityNotAvailableException;
import br.com.puggian.products.api.exception.ResourceNotFoundException;
import br.com.puggian.products.api.model.Product;
import br.com.puggian.products.api.model.Supplier;
import br.com.puggian.products.api.repository.ProductRepository;
import br.com.puggian.products.api.service.ProductService;
import br.com.puggian.products.api.service.SupplierService;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final SupplierService supplierService;

    private static final int MAXIMUM_QUANTITY = 999999999;

    public ProductServiceImpl(ProductRepository productRepository, SupplierService supplierService) {
        this.productRepository = productRepository;
        this.supplierService = supplierService;
    }

    @Override
    public List<Product> listProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(long id) {
        return productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found for id " + id));
    }

    @Override
    public Product createProduct(CreateProductDto dto) {
        Supplier supplier = supplierService.getSupplierById(dto.getSupplierId());
        LocalDateTime now = LocalDateTime.now();
        Product product = Product.builder()
                .name(dto.getName())
                .quantity(0L)
                .price(dto.getPrice())
                .creation(now)
                .lastUpdate(now)
                .supplier(supplier)
                .build();

        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(UpdateProductDto dto, Long id) {
        Product product = getProductById(id);
        LocalDateTime now = LocalDateTime.now();

        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        product.setLastUpdate(now);

        return productRepository.save(product);
    }

    @Override
    public void deleteProduct(Long id) {
        try {
            productRepository.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            throw new ResourceNotFoundException("Product not found for id " + id);
        }
    }

    @Override
    public Product addQuantity(ProductQuantityDto dto, Long id) {
        Product product = getProductById(id);

        if (Math.abs(dto.getQuantity()) > MAXIMUM_QUANTITY
                || product.getQuantity() + dto.getQuantity() > MAXIMUM_QUANTITY) {
            throw new QuantityExceededMaximumValueException("Quantity exceeded maximum value of: " + MAXIMUM_QUANTITY);
        }

        long newQuantity = product.getQuantity() + dto.getQuantity();
        if (newQuantity < 0) {
            throw new QuantityNotAvailableException("Quantity not available. Quantity in stock: " + product.getQuantity());
        }

        LocalDateTime now = LocalDateTime.now();
        product.setLastUpdate(now);
        product.setQuantity(newQuantity);

        return productRepository.save(product);
    }
}
