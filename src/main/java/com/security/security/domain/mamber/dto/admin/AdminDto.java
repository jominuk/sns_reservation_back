package com.security.security.domain.mamber.dto.admin;


import com.security.security.domain.mamber.constant.Role;
import lombok.*;

import java.time.LocalDateTime;

public class AdminDto {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class memberList{
        private Long id;
        private String userId;
        private String memo;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private Role role;
        private boolean isActive;
        private LocalDateTime deactivatedAt;
    }
}
