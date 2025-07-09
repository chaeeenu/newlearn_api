package com.newlearn.auth.controller;

import com.newlearn.common.jwt.JwtProvider;
import com.newlearn.service.dto.user.LoginResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/token")
@RequiredArgsConstructor
public class TokenController {

    private final JwtProvider jwtProvider;

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponseDTO> refreshAccessToken(@RequestParam String refreshToken) {
        if (jwtProvider.validateToken(refreshToken)) {
            String email = jwtProvider.getEmailFromToken(refreshToken);
            String role = jwtProvider.getRoleFromToken(refreshToken); // 필요 시 role도 처리
            String newAccessToken = jwtProvider.generateAccessToken(email, role);

            return ResponseEntity.ok(new LoginResponseDTO(newAccessToken, refreshToken));
        } else {
            return ResponseEntity.status(401).build(); // RefreshToken이 유효하지 않음
        }
    }
}
