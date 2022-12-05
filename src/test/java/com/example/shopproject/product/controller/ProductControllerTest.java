package com.example.shopproject.product.controller;

import com.example.shopproject.shop.controller.ProductController;
import com.example.shopproject.shop.model.Product;
import com.example.shopproject.shop.service.impl.ProductServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static com.example.shopproject.product.ProductForTest.productForTest;

@WebMvcTest(ProductController.class)
@AutoConfigureMockMvc
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductServiceImpl productService;

    @Test
    void getProductsTest() throws Exception {
        Product testObject = new Product("phone1", "new", 122.0, 1L, 3);
        Product testObject1 = new Product("phone2", "new", 122.0, 1L, 3);
        Product testObject2 = new Product("phone3", "new", 122.0, 1L, 3);

        List<Product> expectedShopList = new ArrayList<>(Arrays.asList(testObject, testObject1, testObject2));

        when(productService.findAll()).thenReturn(expectedShopList);

        List<Product> actualShopList = productService.findAll();
        assertEquals(expectedShopList.size(), actualShopList.size());

        mockMvc.perform(MockMvcRequestBuilders.get("/shop/getAll"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void getShopByIdTest() throws Exception {
        when(productService.findById(99L)).thenReturn(productForTest());
        mockMvc.perform(MockMvcRequestBuilders.get("/shop/getById/99")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(productForTest().getName()));
    }

    @Test
    void saveProductTest() throws Exception {
        when(productService.save(any(Product.class))).thenReturn(productForTest());

        mockMvc.perform(MockMvcRequestBuilders.post("/shop/save")
                        .content(new ObjectMapper().writeValueAsString(productForTest()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(productForTest().getName()))
                .andDo(print());
    }

    @Test
    void deleteProductTest() throws Exception {
        when(productService.delete(productForTest().getId())).thenReturn(productForTest());

        mockMvc.perform(MockMvcRequestBuilders.delete("/shop/delete/{id}", 99)
                        .content(new ObjectMapper().writeValueAsString(productForTest()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
