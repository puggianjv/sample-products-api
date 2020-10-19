package br.com.puggian.products.api.service;

import br.com.puggian.products.api.dto.CreateProductDto;
import br.com.puggian.products.api.model.Product;

import java.util.List;

public interface ProductService {

    List<Product> listProducts();

    Product getProductById(long id);

    Product createProduct(CreateProductDto createProductDto);
}
