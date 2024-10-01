package com.security.security.domain.mamber.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberUpdateRequestDto {

    private String password;
    private String memo;
}
