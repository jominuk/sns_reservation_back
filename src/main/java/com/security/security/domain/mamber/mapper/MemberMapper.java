package com.security.security.domain.mamber.mapper;

import com.security.security.domain.mamber.dto.LoginDto;
import com.security.security.domain.mamber.dto.MemberDto;
import com.security.security.domain.mamber.dto.response.MemberInfoDto;
import com.security.security.domain.mamber.entity.MemberEntity;

public class MemberMapper {

    /**
     * entity -> dto 변환
     */
    public static MemberDto toDto(MemberEntity entity) {
        return MemberDto.builder()
                .id(entity.getId())
                .userId(entity.getUserId())
                .password(entity.getPassword())
                .memo(entity.getMemo())
                .createdAt(entity.getCreatedAt())
                .build();
    }
    public static MemberInfoDto toInfoDto(MemberEntity entity) {
        return MemberInfoDto.builder()
                .id(entity.getId())
                .userId(entity.getUserId())
                .memo(entity.getMemo())
                .createdAt(entity.getCreatedAt())
                .build();
    }
    public static LoginDto toLoginDto(MemberEntity entity) {
        return LoginDto.builder()
                .userId(entity.getUserId())
                .role(entity.getRole())
                .build();
    }

    /**
     *  로그인 정보를 dto로 반환
     */
    public static LoginDto createTokenLoginResponseDto(
            LoginDto memberDto, String accessToken, String refreshToken, String userId
    ) {
        LoginDto loginDto = new LoginDto();
        loginDto.setUserId(userId);
        loginDto.setPassword(memberDto.getPassword());
        loginDto.setAccessToken(accessToken);
        loginDto.setRefreshToken(refreshToken);
        return loginDto;
    }
}
