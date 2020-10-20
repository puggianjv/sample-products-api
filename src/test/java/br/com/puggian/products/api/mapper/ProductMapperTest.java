package br.com.puggian.products.api.mapper;

import br.com.puggian.products.api.dto.output.ProductDto;
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
public class ProductMapperTest {

    @Autowired
    private ProductMapper productMapper;

    @Test
    public void convertProductToDto_ProductListConverted_DtoList() {
        LocalDateTime dateTime = LocalDateTime.now();
        Supplier supplier = new Supplier(1L, "Extra", dateTime, dateTime, null);
        Product product1 = new Product(1L, "Trakinas", 10L, BigDecimal.TEN, dateTime, dateTime, supplier, false);
        Product product2 = new Product(2L, "Doritos", 10L, BigDecimal.TEN, dateTime, dateTime, supplier, false);
        List<Product> products = Arrays.asList(product1, product2);
        supplier.setProducts(products);

        List<ProductDto> list = productMapper.convertProductToDto(products);

        assertEquals(2, list.size());
        assertEquals(new Long(1L), list.get(0).getId());
        assertEquals("Trakinas", list.get(0).getName());
        assertEquals(new Long(1L), list.get(0).getSupplier().getId());
        assertEquals("Extra", list.get(0).getSupplier().getName());
        assertEquals(new Long(2L), list.get(1).getId());
        assertEquals(new Long(1L), list.get(0).getSupplier().getId());
    }

    @Test
    public void convertProductToDto_ProductConverted_Dto() {
        LocalDateTime dateTime = LocalDateTime.now();
        Supplier supplier = new Supplier(1L, "Extra", dateTime, dateTime, null);
        Product product = new Product(1L, "Trakinas", 10L, BigDecimal.TEN, dateTime, dateTime, supplier, false);
        List<Product> products = Collections.singletonList(product);
        supplier.setProducts(products);

        ProductDto dto = productMapper.convertProductToDto(product);

        assertEquals(new Long(1L), dto.getId());
        assertEquals("Trakinas", dto.getName());
        assertEquals(new Long(1L), dto.getSupplier().getId());
        assertEquals("Extra", dto.getSupplier().getName());
    }
}