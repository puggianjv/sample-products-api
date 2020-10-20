package br.com.puggian.products.api.mapper;


import br.com.puggian.products.api.dto.output.SupplierDto;
import br.com.puggian.products.api.model.Supplier;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class SupplierMapper {

    private final ModelMapper mapper;

    public SupplierMapper(ModelMapper modelMapper){
        this.mapper = modelMapper;
    }

    public SupplierDto convertSupplierToDto(Supplier supplier) {
        return mapper.map(supplier, SupplierDto.class);
    }

}
