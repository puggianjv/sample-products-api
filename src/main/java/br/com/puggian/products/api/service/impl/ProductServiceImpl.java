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
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

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
    public List<Product> listProducts(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return productRepository.findAll(pageRequest).getContent();
    }

    @Override
    public Product getProductById(long id) {
        return productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found for id " + id));
    }

    @Override
    public Product createProduct(CreateProductDto dto) {
        Supplier supplier = supplierService.getSupplierById(dto.getSupplierId());
        Product product = Product.builder()
                .name(dto.getName())
                .quantity(0L)
                .price(dto.getPrice())
                .supplier(supplier)
                .build();

        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(UpdateProductDto dto, Long id) {
        Product product = getProductById(id);
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());

        return productRepository.save(product);
    }

    @Override
    public void softDelete(Long id) {
        Product product = this.getProductById(id);
        product.setDeleted();

        productRepository.save(product);
    }

    @Override
    public Product addQuantity(ProductQuantityDto dto, Long id) {
        Product product = this.getProductById(id);

        if (Math.abs(dto.getQuantity()) > MAXIMUM_QUANTITY
                || product.getQuantity() + dto.getQuantity() > MAXIMUM_QUANTITY) {
            throw new QuantityExceededMaximumValueException("Quantity exceeded maximum value of: " + MAXIMUM_QUANTITY);
        }

        long newQuantity = product.getQuantity() + dto.getQuantity();
        if (newQuantity < 0) {
            throw new QuantityNotAvailableException("Quantity not available. Quantity in stock: " + product.getQuantity());
        }

        product.setQuantity(newQuantity);

        return productRepository.save(product);
    }
}
