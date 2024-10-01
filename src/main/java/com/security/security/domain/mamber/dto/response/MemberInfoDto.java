package com.security.security.domain.mamber.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberInfoDto {
    private Long id;
    private String userId;
    private String memo;
    private LocalDateTime createdAt;
}
