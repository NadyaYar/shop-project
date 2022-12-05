package com.example.shopproject.shop.service;

import com.example.shopproject.shop.dto.CartDto;
import com.example.shopproject.shop.model.Cart;
import com.example.shopproject.user.model.User;

public interface CartService {

     void addToCart(Cart addToCartDto, User user);

     CartDto listCartItems(User user);

     void deleteCartItem(Long itemId,User user);

     void checkout(User user);
}
