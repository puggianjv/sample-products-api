package br.com.puggian.products.api.controller;

import br.com.puggian.products.api.dto.CreateProductDto;
import br.com.puggian.products.api.dto.ProductDto;
import br.com.puggian.products.api.model.Product;
import br.com.puggian.products.api.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
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
    @ResponseStatus(HttpStatus.OK)
    public List<ProductDto> listProducts() {
        return convertToDto(productService.listProducts());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductDto getProduct(@PathVariable("id") long id) {
        return convertToDto(productService.getProductById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDto insertProduct(@Valid @RequestBody CreateProductDto createProductDto) {
        return convertToDto(productService.createProduct(createProductDto));
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
