package com.example.shopproject.shop.service.impl;

import com.example.shopproject.shop.dto.CartDto;
import com.example.shopproject.shop.dto.CartItemDto;
import com.example.shopproject.shop.exception.ProductNotFoundException;
import com.example.shopproject.shop.model.Cart;
import com.example.shopproject.shop.model.Product;
import com.example.shopproject.shop.repository.CartRepository;
import com.example.shopproject.shop.service.CartService;
import com.example.shopproject.shop.time.ScheduleCart;
import com.example.shopproject.user.exception.NoEnoughMoneyException;
import com.example.shopproject.user.model.User;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;

    private final ProductServiceImpl productService;

    private final ScheduleCart scheduleCart;


    @SneakyThrows
    public void addToCart(Cart addToCartDto, User user) {
        Product product = productService.findById(addToCartDto.getProduct().getId());

        Cart cart = new Cart();
        cart.setProduct(product);
        cart.setUser(user);
        cart.setQuantity(addToCartDto.getQuantity());
        cart.setCreatedDate(new Date());

        if (cart.getQuantity() > product.getQuantity()) {
            throw new RuntimeException("We have not such quantity");
        } else {
            product.setQuantity(product.getQuantity() - cart.getQuantity());
        }

        if (cart.getQuantity() == 0) {
            throw new RuntimeException("Please,choose quantity");
        }
        
        cartRepository.save(cart);
        scheduleCart.ScheduleCartTask();

    }

    public CartDto listCartItems(User user) {
        List<Cart> cartList = cartRepository.findAllByUserOrderByCreatedDateDesc(user);
        List<CartItemDto> cartItems = new ArrayList<>();
        double totalCost = 0;
        for (Cart cart : cartList) {
            CartItemDto cartItemDto = new CartItemDto(cart);
            totalCost += cartItemDto.getQuantity() * cart.getProduct().getPrice();
            cartItems.add(cartItemDto);
        }

        CartDto cartDto = new CartDto();
        cartDto.setTotalCost(totalCost);
        cartDto.setCartItems(cartItems);
        return cartDto;
    }


    public void deleteCartItem(Long itemId, User user) {

        Optional<Cart> optionalCart = cartRepository.findById(itemId);

        if (optionalCart.isEmpty()) {
            throw new ProductNotFoundException("cart item id is invalid: " + itemId);
        }

        Cart cart = optionalCart.get();

        cartRepository.delete(cart);

    }

    public void checkout(User user) {
        double balance = user.getBalance();
        double totalCoast = listCartItems(user).getTotalCost();
        if (balance < totalCoast) {
            throw new NoEnoughMoneyException("Sorry, you have not enough money");
        }
        double result = balance - totalCoast;
        deleteAll();
    }

    private void deleteAll() {
        List<Cart> optionalCart = cartRepository.findAll();
        cartRepository.deleteAll(optionalCart);
    }
}
