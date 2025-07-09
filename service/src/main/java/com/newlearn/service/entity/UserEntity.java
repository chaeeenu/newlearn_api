package com.newlearn.service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", length = 5, nullable = false, unique = true, columnDefinition = "유저 내부식별자")
    private Integer id;
    
    @Column(name = "email", length = 100, nullable = false, unique = true, columnDefinition = "계정 email(ID)")
    private String email;
    @Column(name = "pwd", length = 255, nullable = false, columnDefinition = "계정 비밀번호")
    private String pwd;
    @Column(name = "name", length = 100, nullable = false, columnDefinition = "사용자명")
    private String name;
    @Column(name = "mobile_phone_num", length = 11, nullable = false, columnDefinition = "휴대전화번호")
    private String mobilePhoneNum;
    @Column(name = "status", length = 20, nullable = false, columnDefinition = "계정 상태")
    private String status;
    @Column(name = "profile_image_url", length = 255, columnDefinition = "프로필 이미지 URL")
    private String profileImageUrl;
    @Column(name = "reg_ip", length = 45, nullable = false, columnDefinition = "등록자IP")
    private String regIp;
    @Column(name = "reg_dtm", nullable = false, columnDefinition = "등록일시")
    private LocalDateTime regDtm;
    @Column(name = "mod_ip", length = 45, nullable = false, columnDefinition = "수정자IP")
    private String modIp;
    @Column(name = "mod_dtm", nullable = false, columnDefinition = "수정일시")
    private LocalDateTime modDtm;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<UserRoleEntity> roles;
}
