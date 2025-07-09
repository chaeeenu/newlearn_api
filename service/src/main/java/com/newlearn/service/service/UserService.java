package com.newlearn.service.service;

import com.newlearn.common.jwt.JwtProvider;
import com.newlearn.common.util.EncryptionUtil;
import com.newlearn.service.dto.user.LoginResponseDTO;
import com.newlearn.service.dto.user.UserDTO;
import com.newlearn.service.entity.UserEntity;
import com.newlearn.service.entity.UserRoleEntity;
import com.newlearn.service.entity.ids.UserRoleId;
import com.newlearn.service.enums.Status;
import com.newlearn.service.repository.UserRepository;
import com.newlearn.service.repository.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static java.time.LocalDateTime.now;

@Service
@RequiredArgsConstructor
public class UserService {

    @Value("${app.role.user.default}")
    private String defaultRole;

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final JwtProvider jwtProvider;

    @Transactional
    public void signUp(UserDTO userDTO, String ip) {
        // 1. 이메일 중복 검사
        if(userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }

        // 2. 회원가입 사용자 정보 DB저장
        UserEntity newUser = UserEntity.builder()
                .email(userDTO.getEmail())
                .pwd(userDTO.getPwd())
                .name(userDTO.getName())
                .mobilePhoneNum(userDTO.getMobilePhoneNum())        // 암호화필드 여부 검토 필요
                .status(Status.USER_ACTIVE.getCode())
                .profileImageUrl(userDTO.getProfileImageUrl())
                .regIp(ip)
                .regDtm(now())
                .modIp(ip)
                .modDtm(now())
                .build();
        userRepository.save(newUser);
        
        // 3. 사용자 권한정보 DB 저장
        UserRoleEntity newUserRole = UserRoleEntity.builder()
                .id(new UserRoleId(newUser.getId(), defaultRole))
                .user(newUser)
                .build();
        userRoleRepository.save(newUserRole);
    }

    public LoginResponseDTO login(String email, String rawPwd) {

        // 1. 사용자 존재여부 확인
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 사용자입니다."));

        // 2. 비밀번호 일치여부 확인
        if(!EncryptionUtil.matches(rawPwd, user.getPwd())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        // 3. 로그인 확인 완료시, 토큰발급
        String accessToken = jwtProvider.generateAccessToken(user.getEmail(), user.getPwd());
        String refreshToken = jwtProvider.generateRefreshToken(user.getEmail());

        return new LoginResponseDTO(accessToken, refreshToken);
    }
}
