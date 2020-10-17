package br.com.puggian.products.api.service;

import br.com.puggian.products.api.model.Product;
import br.com.puggian.products.api.repository.ProductRepository;
import br.com.puggian.products.api.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> listProducts() {
        return productRepository.findAll();
    }
}
