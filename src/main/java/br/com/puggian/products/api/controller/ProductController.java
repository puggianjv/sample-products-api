package br.com.puggian.products.api.controller;

import br.com.puggian.products.api.dto.ProductDto;
import br.com.puggian.products.api.model.Product;
import br.com.puggian.products.api.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value="/products")
public class ProductController {

    private final ProductService productService;

    private final ModelMapper mapper;

    public ProductController(ProductService productService, ModelMapper modelMapper){
        this.productService = productService;
        this.mapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> listProducts() {
        List<Product> products = productService.listProducts();
        return ResponseEntity.ok(convertToDto(products));
    }

    private List<ProductDto> convertToDto(List<Product> products) {
        return products.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private ProductDto convertToDto(Product product) {
        return mapper.map(product, ProductDto.class);
    }

}
