package com.example.shopproject.user.service;

import com.example.shopproject.security.token.AuthenticationFailException;
import com.example.shopproject.security.token.AuthenticationService;
import com.example.shopproject.security.token.AuthenticationToken;
import com.example.shopproject.security.token.TokenNotFoundException;
import com.example.shopproject.user.dto.ResponseDto;
import com.example.shopproject.user.dto.SignInDto;
import com.example.shopproject.user.dto.SignInResponseDto;
import com.example.shopproject.user.dto.SignupDto;
import com.example.shopproject.user.exception.InvalidBalanceException;
import com.example.shopproject.user.exception.UserExistException;
import com.example.shopproject.user.exception.UserNotFoundException;
import com.example.shopproject.user.model.User;
import com.example.shopproject.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AuthenticationService authenticationService;

    @Transactional
    public ResponseDto signUp(SignupDto signupDto) {
        if (Objects.nonNull(userRepository.findByEmail(signupDto.getEmail()))) {
            throw new UserExistException("user already present");
        }

        String encryptedPassword = signupDto.getPassword();

        try {
            encryptedPassword = hashPassword(signupDto.getPassword());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        User user = new User(signupDto.getFirstName(), signupDto.getLastName(),
                signupDto.getEmail(), encryptedPassword,signupDto.getBalance());

        userRepository.save(user);

        final AuthenticationToken authenticationToken = new AuthenticationToken(user);

        authenticationService.saveConfirmationToken(authenticationToken);

        return new ResponseDto("success", "user created successfully");
    }

    private String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());
        byte[] digest = md.digest();
        return DatatypeConverter
                .printHexBinary(digest).toUpperCase();
    }

    public SignInResponseDto signIn(SignInDto signInDto) {

        User user = userRepository.findByEmail(signInDto.getEmail());

        if (Objects.isNull(user)) {
            throw new AuthenticationFailException("user is not valid");
        }

        try {
            if (!user.getPassword().equals(hashPassword(signInDto.getPassword()))) {
                throw new AuthenticationFailException("wrong password");
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        AuthenticationToken token = authenticationService.getToken(user);


        if (Objects.isNull(token)) {
            throw new TokenNotFoundException("token is not present");
        }

        return new SignInResponseDto("success", token.getToken());
    }

    private User findById(Long id) {
        Optional<User> user = userRepository.findById(id);

        if (user.isEmpty()) {
            throw new UserNotFoundException("No user with id: " + id);
        }
        return user.get();
    }

    public double putMoney(Long userId, double money) {
        if (money <= 0) {
            throw new InvalidBalanceException("can`t add negative money");
        }
        User user = findById(userId);
        user.setBalance(user.getBalance() + money);
        return user.getBalance();
    }
}
