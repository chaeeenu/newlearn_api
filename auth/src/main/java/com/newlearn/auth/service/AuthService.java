package com.newlearn.auth.service;

import com.newlearn.service.dto.user.LoginResponseDTO;
import com.newlearn.service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class   AuthService {

    private final UserService userService;

    public LoginResponseDTO login(String email, String pwd) {
        return userService.login(email, pwd);
    }

}
