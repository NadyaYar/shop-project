package com.example.shopproject.user.service;

import com.example.shopproject.user.dto.ResponseDto;
import com.example.shopproject.user.dto.SignInDto;
import com.example.shopproject.user.dto.SignInResponseDto;
import com.example.shopproject.user.dto.SignupDto;

public interface UserService {
    ResponseDto signUp(SignupDto signupDto);

    SignInResponseDto signIn(SignInDto signInDto);

    double putMoney(Long userId, double money);

}
