package com.security.security.domain.list.service;

import com.security.security.domain.list.dto.ListDto;
import com.security.security.domain.list.entity.ListEntity;
import com.security.security.domain.list.mapper.ListMapper;
import com.security.security.domain.list.repository.ListRepository;
import com.security.security.domain.mamber.entity.MemberEntity;
import com.security.security.domain.mamber.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ListService {

    private final ListRepository listRepository;
    private final MemberRepository memberRepository;
    private final S3UploadService s3UploadService;

    /**
     * 게시글 등록
     */
    @Transactional
    public ListDto listPost(ListDto dto, MultipartFile image) {
        if (dto == null || dto.getTitle() == null || dto.getTitle().trim().isEmpty()
                || dto.getContent() == null || dto.getContent().trim().isEmpty()) {
            throw new RuntimeException("제목과 내용을 모두 입력해주세요.");
        }

        MemberEntity member = memberRepository.findByUserId(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        String imageUrl = null;
        if (image != null && !image.isEmpty()) {
            try {
                imageUrl = s3UploadService.upload(image);
            } catch (IOException e) {
                throw new RuntimeException("이미지 업로드에 실패했습니다.", e);
            }
        }
        dto.setImageUrl(imageUrl);

        ListEntity entity = ListMapper.toEntity(dto, member);
        ListEntity savedEntity = listRepository.save(entity);
        return ListMapper.toDto(savedEntity);
    }

    /**
     * 게시글 해당 id 목록
     */
    @Transactional
    public List<ListDto> listGet(String userId) {
        List<ListEntity> listEntityList = listRepository.findByMember_UserId(userId);

        Collections.reverse(listEntityList);

        return listEntityList.stream()
                .map(ListMapper::toDto)
                .collect(Collectors.toList());
    }

}

