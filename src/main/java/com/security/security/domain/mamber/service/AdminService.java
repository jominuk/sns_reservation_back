package com.security.security.domain.mamber.service;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.security.security.domain.mamber.dto.admin.AdminDto;
import com.security.security.domain.mamber.entity.MemberEntity;
import com.security.security.domain.mamber.mapper.AdminMapper;
import com.security.security.domain.mamber.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.security.security.domain.mamber.entity.QMemberEntity.memberEntity;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminService {

    private final JPAQueryFactory queryFactory;
    private final MemberRepository memberRepository;

    /**
     * 회원 목록 가져오기
     */
    @Transactional
    public Page<AdminDto.memberList> adminMember(Pageable pageable) {
        JPAQuery<MemberEntity> query = queryFactory
                .selectFrom(memberEntity)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        List<MemberEntity> entities = query.fetch();

        Long totalCount = queryFactory
                .select(memberEntity.count())
                .from(memberEntity)
                .fetchOne();

        long safeTotalCount = totalCount != null ? totalCount : 0L;

        List<AdminDto.memberList> memberLists = entities.stream()
                .map(AdminMapper::toAdminMemberList)
                .collect(Collectors.toList());

        return new PageImpl<>(memberLists, pageable, safeTotalCount);
    }


}
