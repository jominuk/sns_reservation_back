package com.security.security.domain.list.mapper;

import com.security.security.domain.list.dto.ListDto;
import com.security.security.domain.list.entity.ListEntity;
import com.security.security.domain.mamber.entity.MemberEntity;


public class ListMapper {

    // entity -> dto로 변환
    public static ListDto toDto(ListEntity entity) {
        return ListDto.builder()
                .id(entity.getId())
                .imageUrl(entity.getImageUrl())
                .title(entity.getTitle())
                .content(entity.getContent())
                .userId(entity.getMember().getUserId())
                .createDate(entity.getCreatedAt())
                .build();
    }

    // dto -> entity로 변환
    public static ListEntity toEntity(ListDto dto, MemberEntity member) {
        return ListEntity.builder()
                .id(dto.getId())
                .imageUrl(dto.getImageUrl())
                .title(dto.getTitle())
                .content(dto.getContent())
                .member(member)
                .build();
    }
}
