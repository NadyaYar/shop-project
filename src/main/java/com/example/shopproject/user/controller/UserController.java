package com.example.shopproject.user.controller;

import com.example.shopproject.security.token.AuthenticationService;
import com.example.shopproject.shop.ApiResponse;
import com.example.shopproject.user.dto.ResponseDto;
import com.example.shopproject.user.dto.SignInDto;
import com.example.shopproject.user.dto.SignInResponseDto;
import com.example.shopproject.user.dto.SignupDto;
import com.example.shopproject.user.model.User;
import com.example.shopproject.user.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userService;
    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseDto signup(@RequestBody SignupDto signupDto) {
        return userService.signUp(signupDto);
    }

    @PostMapping("/signIn")
    public SignInResponseDto signIn(@RequestBody SignInDto signInDto) {
        return userService.signIn(signInDto);
    }

    @PutMapping("/putMoney/{money}")
    public ResponseEntity<ApiResponse> putMoney(@PathVariable("money") double money,
                                                @RequestParam("token") String token) {

        authenticationService.authenticate(token);

        User user = authenticationService.getUser(token);

        userService
                .putMoney(user.getId(), money);
        return new ResponseEntity<>(new ApiResponse(true,
                "money was added" +
                        user.getBalance()),
                HttpStatus.OK);
    }

}
