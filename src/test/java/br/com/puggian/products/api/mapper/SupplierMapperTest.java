package br.com.puggian.products.api.mapper;

import br.com.puggian.products.api.dto.output.ProductDto;
import br.com.puggian.products.api.dto.output.SupplierDto;
import br.com.puggian.products.api.model.Product;
import br.com.puggian.products.api.model.Supplier;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SupplierMapperTest {

    @Autowired
    private SupplierMapper supplierMapper;

    @Test
    public void convertSupplierToDto_SupplierConverted_Dto() {
        LocalDateTime dateTime = LocalDateTime.now();
        Supplier supplier = new Supplier(1L, "Extra", Collections.emptyList(), dateTime, dateTime);

        SupplierDto dto = supplierMapper.convertSupplierToDto(supplier);

        assertEquals(new Long(1L), dto.getId());
        assertEquals("Extra", dto.getName());
    }
}