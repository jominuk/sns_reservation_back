package com.security.security.domain.mamber.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberDto {
    private Long id;
    private String userId;
    private String password;
    private String memo;
    private LocalDateTime createdAt;
}
