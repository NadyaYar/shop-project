package com.example.shopproject.shop.controller;

import com.example.shopproject.security.token.AuthenticationService;
import com.example.shopproject.shop.ApiResponse;
import com.example.shopproject.shop.dto.CartDto;
import com.example.shopproject.shop.model.Cart;
import com.example.shopproject.shop.service.impl.CartServiceImpl;
import com.example.shopproject.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartServiceImpl cartService;

    private final AuthenticationService authenticationService;

    @PostMapping("/addItem")
    public ResponseEntity<com.example.shopproject.shop.ApiResponse> addToCart(@RequestBody Cart cart,
                                                                              @RequestParam("token") String token) {
        authenticationService.authenticate(token);

        User user = authenticationService.getUser(token);

        cartService.addToCart(cart, user);

        return new ResponseEntity<>(new ApiResponse(true, "Added to cart"), HttpStatus.CREATED);
    }


    @GetMapping("/getAllItems")
    public ResponseEntity<CartDto> getCartItems(@RequestParam("token") String token) {
        authenticationService.authenticate(token);

        User user = authenticationService.getUser(token);

        CartDto cartDto = cartService.listCartItems(user);
        return new ResponseEntity<>(cartDto, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{cartItemId}")
    public ResponseEntity<ApiResponse> deleteCartItem(@PathVariable("cartItemId") Long itemId,
                                                      @RequestParam("token") String token) {

        authenticationService.authenticate(token);

        User user = authenticationService.getUser(token);

        cartService.deleteCartItem(itemId, user);

        return new ResponseEntity<>(new ApiResponse(true, "Item has been removed"), HttpStatus.OK);

    }

    @GetMapping("/checkout")
    public ResponseEntity<ApiResponse> checkout(@RequestParam("token") String token) {
        authenticationService.authenticate(token);

        User user = authenticationService.getUser(token);
        cartService.checkout(user);
        return new ResponseEntity<>(new ApiResponse(true,
                "Item was paid. Your balance :  " + user.getBalance()),
                HttpStatus.OK);
    }
}
