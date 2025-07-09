package com.newlearn.client.controller;

import com.newlearn.auth.service.AuthService;
import com.newlearn.service.dto.user.LoginRequestDTO;
import com.newlearn.service.dto.user.LoginResponseDTO;
import com.newlearn.service.dto.user.UserDTO;
import com.newlearn.service.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final AuthService authService;

    @PostMapping("/signup")
    public void signUp(@RequestBody UserDTO userDTO, HttpServletRequest request) {
        userService.signUp(userDTO, request.getRemoteAddr());
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginDTO, HttpServletRequest request) {
        LoginResponseDTO response = authService.login(loginDTO.getEmail(), loginDTO.getPwd());
        return ResponseEntity.ok(response);

    }
}
