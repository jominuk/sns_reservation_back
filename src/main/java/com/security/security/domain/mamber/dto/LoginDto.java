package com.security.security.domain.mamber.dto;

import com.security.security.domain.mamber.constant.Role;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginDto {
    private String userId;
    private String password;
    private String accessToken;
    private Role role;
    private String refreshToken;
}
