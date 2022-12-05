package com.example.shopproject.shop.service.impl;

import com.example.shopproject.shop.exception.ProductExistException;
import com.example.shopproject.shop.exception.ProductNotFoundException;
import com.example.shopproject.shop.model.Product;
import com.example.shopproject.shop.repository.ProductRepository;
import com.example.shopproject.shop.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public Product save(Product product) {
        if (productRepository.existsById(product.getId())) {
            throw new ProductExistException("Product with id : " + product.getId()
                    + " already exist");
        }

        return productRepository.save(product);
    }

    @Override
    public Product update(Product newProduct, long id) {
            isExists(newProduct.getId());
            Product product = productRepository.findById(id);

            assert product != null;
            product.setName(product.getName());
            product.setPrice(product.getPrice());
            product.setVersion(product.getVersion());
            product.setDescription(product.getDescription());
            product.setQuantity(newProduct.getQuantity());
            return product;
        }

    @Override
    public Product delete(long id) {
        productRepository.delete(findById(id));
        return null;
    }

    @Override
    public Product findById(long id) {
        isExists(id);
        return productRepository.findById(id);
    }

    @Override
    public Product findByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public void isExists(long id) throws ProductNotFoundException {
        boolean isExists = productRepository.existsById(id);
        if (!isExists) {
            throw new ProductNotFoundException("Product with id: " + id + " not found");
        }
    }
}
