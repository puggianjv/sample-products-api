package br.com.puggian.products.api.mapper;


import br.com.puggian.products.api.dto.output.ProductDto;
import br.com.puggian.products.api.model.Product;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductMapper {

    private final ModelMapper mapper;

    public ProductMapper(ModelMapper modelMapper){
        this.mapper = modelMapper;
    }

    public List<ProductDto> convertProductToDto(List<Product> products) {
        return products.stream()
                .map(this::convertProductToDto)
                .collect(Collectors.toList());
    }

    public ProductDto convertProductToDto(Product product) {
        return mapper.map(product, ProductDto.class);
    }

}
