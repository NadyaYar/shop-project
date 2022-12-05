package com.example.shopproject.product.repository;

import com.example.shopproject.shop.model.Product;
import com.example.shopproject.shop.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.shopproject.product.ProductForTest.productForTest;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
public class ProductRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ProductRepository productRepository;

    @Test
    void saveProductTest() {
        Product product = new Product("phone", "new", 122.0, 1L, 3);
        entityManager.persistAndFlush(product);

        assertEquals(productRepository.findById(product.getId()), Optional.of(product));
    }

    @Test
    void findAllProductsTest() {
        productRepository.save(productForTest());
        List<Product> productResult = new ArrayList<>(productRepository.findAll());
        assertEquals(productResult.size(), 1);
    }

    @Test
    public void whenFindByNameThenReturnProduct() {
        Product testObject = new Product("phone", "new", 122.0, 1L, 3);
        entityManager.persistAndFlush(testObject);

        Product found = productRepository.findByName(testObject.getName());
        assertThat(found.getName()).isEqualTo(testObject.getName());
    }

    @Test
    public void whenFindByIdThenReturnProduct() {
        Product testObject = new Product("phone", "new", 122.0, 1L, 3);
        entityManager.persistAndFlush(testObject);

        Optional<Product> found = productRepository.findById(testObject.getId());
        assertThat(found.get().getId()).isEqualTo(testObject.getId());
    }
}
