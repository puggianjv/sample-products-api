package br.com.puggian.products.api.controller;

import br.com.puggian.products.api.dto.input.CreateProductDto;
import br.com.puggian.products.api.dto.input.ProductQuantityDto;
import br.com.puggian.products.api.dto.input.UpdateProductDto;
import br.com.puggian.products.api.dto.output.ProductDto;
import br.com.puggian.products.api.mapper.ProductMapper;
import br.com.puggian.products.api.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value="/products")
public class ProductController {

    private final ProductService productService;
    private final ProductMapper mapper;

    public ProductController(ProductService productService, ProductMapper mapper){
        this.productService = productService;
        this.mapper = mapper;
    }

    @GetMapping(params = { "page", "size" })
    @ResponseStatus(HttpStatus.OK)
    public List<ProductDto> listProducts(@RequestParam(value = "page", defaultValue = "0") int page,
                                         @RequestParam(value = "size", defaultValue = "100") int size) {
        return mapper.convertProductToDto(productService.listProducts(page, size));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductDto getProduct(@PathVariable("id") long id) {
        return mapper.convertProductToDto(productService.getProductById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDto insertProduct(@Valid @RequestBody CreateProductDto dto) {
        return mapper.convertProductToDto(productService.createProduct(dto));
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductDto updateProduct(@Valid @RequestBody UpdateProductDto dto, @PathVariable Long id) {
        return mapper.convertProductToDto(productService.updateProduct(dto, id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable Long id) {
        productService.softDelete(id);
    }

    @PatchMapping("/{id}/add")
    @ResponseStatus(HttpStatus.OK)
    public ProductDto addQuantity(@Valid @RequestBody ProductQuantityDto dto, @PathVariable Long id) {
        return mapper.convertProductToDto(productService.addQuantity(dto, id));
    }
}
