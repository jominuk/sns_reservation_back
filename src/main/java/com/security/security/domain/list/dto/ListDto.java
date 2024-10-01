package com.security.security.domain.list.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ListDto {
    private Long id;
    private String imageUrl;
    private String title;
    private String content;
    private String userId;
    private LocalDateTime createDate = LocalDateTime.now();
}
