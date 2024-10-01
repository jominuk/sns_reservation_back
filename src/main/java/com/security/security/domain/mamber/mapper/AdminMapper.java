package com.security.security.domain.mamber.mapper;

import com.security.security.domain.mamber.dto.admin.AdminDto;
import com.security.security.domain.mamber.entity.MemberEntity;

public class AdminMapper {

    public static AdminDto.memberList toAdminMemberList(MemberEntity entity) {
        return AdminDto.memberList.builder()
                .id(entity.getId())
                .userId(entity.getUserId())
                .memo(entity.getMemo())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .role(entity.getRole())
                .isActive(entity.getIsActive())
                .deactivatedAt(entity.getDeactivatedAt())
                .build();
    }
}
