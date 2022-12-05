package com.example.shopproject.product.service;

import com.example.shopproject.shop.exception.ProductNotFoundException;
import com.example.shopproject.shop.model.Product;
import com.example.shopproject.shop.repository.ProductRepository;
import com.example.shopproject.shop.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ContextConfiguration;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import static com.example.shopproject.product.ProductForTest.productForTest;

@ContextConfiguration(classes = {ProductServiceImpl.class})
@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {


    @Mock
    private ModelMapper modelMapper;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    void saveTest() {
        Product expectedProduct = productForTest();
        when(this.productRepository.save(any())).thenReturn(expectedProduct);

        Product createProduct = new Product();
        createProduct.setName(expectedProduct.getName());
        createProduct.setVersion(expectedProduct.getVersion());
        createProduct.setDescription(expectedProduct.getDescription());
        createProduct.setPrice(expectedProduct.getPrice());
        assertEquals(expectedProduct.getId(), productService.save(createProduct).getId());
    }

    @Test
    void getProductByIdTest() {

        when(productRepository.existsById(productForTest().getId())).thenReturn(true);
        when(productRepository.findById(99L)).thenReturn(productForTest());

        Product actualProduct = productService.findById(99L);

        assertEquals("phone", actualProduct.getName());
        assertEquals(12, actualProduct.getPrice());
        assertEquals("new", actualProduct.getDescription());
        assertEquals(2L, actualProduct.getVersion());
    }

    @Test
    void getAllProductsTest() {
        Product product = new Product();
        Product product1 = new Product();
        Product product2 = new Product();
        Product product3 = new Product();

        List<Product> expectedProductList = new ArrayList<>(Arrays.asList(product, product1, product2, product3));

        when(productRepository.findAll()).thenReturn(expectedProductList);

        List<Product> actualShopList = (List<Product>) productService.findAll();
        assertEquals(expectedProductList.size(), actualShopList.size());
    }

    @Test
    void updateProductTest() {
        when(productRepository.existsById(productForTest().getId())).thenReturn(true);
        when(productRepository.findById(99L)).thenReturn((productForTest()));

        Product actualProduct = productService.update(productForTest(),99L);

        assertEquals(actualProduct.getName(), productForTest().getName());
    }

    @Test
    void deleteTest() {
        when(productRepository.existsById(productForTest().getId())).thenReturn(true);

        Product actualProduct = productService.delete(productForTest().getId());
        assertThat(actualProduct).isNull();
    }

    @Test
    void productNotFoundExceptionTest() {
        assertThrows(ProductNotFoundException.class, () -> productService.isExists(5L));
    }
}



