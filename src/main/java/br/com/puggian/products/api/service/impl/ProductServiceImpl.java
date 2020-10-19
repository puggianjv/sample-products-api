package br.com.puggian.products.api.service.impl;

import br.com.puggian.products.api.dto.CreateProductDto;
import br.com.puggian.products.api.exception.ResourceNotFoundException;
import br.com.puggian.products.api.model.Product;
import br.com.puggian.products.api.model.Supplier;
import br.com.puggian.products.api.repository.ProductRepository;
import br.com.puggian.products.api.service.ProductService;
import br.com.puggian.products.api.service.SupplierService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final SupplierService supplierService;

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
    public Product createProduct(CreateProductDto createProductDto) {
        Supplier supplier = supplierService.getSupplierById(createProductDto.getSupplierId());
        LocalDateTime now = LocalDateTime.now();
        Product product = Product.builder()
                .name(createProductDto.getName())
                .quantity(0L)
                .price(createProductDto.getPrice())
                .creation(now)
                .lastUpdate(now)
                .supplier(supplier)
                .build();

        return productRepository.save(product);
    }
}
