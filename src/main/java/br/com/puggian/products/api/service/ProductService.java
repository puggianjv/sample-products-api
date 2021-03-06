package br.com.puggian.products.api.service;

import br.com.puggian.products.api.dto.input.CreateProductDto;
import br.com.puggian.products.api.dto.input.ProductQuantityDto;
import br.com.puggian.products.api.dto.input.UpdateProductDto;
import br.com.puggian.products.api.model.Product;

import java.util.List;

public interface ProductService {

    List<Product> listProducts(int page, int size);

    Product getProductById(long id);

    Product createProduct(CreateProductDto dto);

    Product updateProduct(UpdateProductDto dto, Long id);

    void softDelete(Long id);

    Product addQuantity(ProductQuantityDto dto, Long id);
}
