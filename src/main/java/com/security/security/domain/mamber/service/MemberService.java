package com.security.security.domain.mamber.service;

import com.security.security.domain.list.entity.ListEntity;
import com.security.security.domain.list.repository.ListRepository;
import com.security.security.domain.mamber.constant.Role;
import com.security.security.domain.mamber.dto.LoginDto;
import com.security.security.domain.mamber.dto.MemberDto;
import com.security.security.domain.mamber.dto.request.MemberUpdateRequestDto;
import com.security.security.domain.mamber.dto.response.MemberInfoDto;
import com.security.security.domain.mamber.entity.MemberEntity;
import com.security.security.domain.mamber.mapper.MemberMapper;
import com.security.security.domain.mamber.repository.MemberRepository;
import com.security.security.jwt.JwtTokenProvider;
import com.security.security.jwt.refresh.TokenEntity;
import com.security.security.jwt.refresh.TokenRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    private final MemberRepository memberRepository;
    private final TokenRepository tokenRepository;
    private final ListRepository listRepository;

    /**
     *  회원가입
     */
    @Transactional
    public MemberDto memberSave(MemberDto dto) {
        String hashedPassword = passwordEncoder.encode(dto.getPassword());

        log.info("Processing save request for user ID: {}", dto.getUserId());

        final var entity = new MemberEntity();

        Optional<MemberEntity> existingMember = memberRepository.findByUserId(dto.getUserId());
        if (existingMember.isPresent()) {
            log.warn("User ID already exists: {}", dto.getUserId());
            throw new RuntimeException("아이디가 이미 존재합니다. 확인해주세요");
        }

        entity.setUserId(dto.getUserId());
        entity.setPassword(hashedPassword);
        entity.setMemo(dto.getMemo());
        entity.setRole(Role.USER);

        MemberEntity savedEntity = memberRepository.save(entity);
        log.info("Successfully saved member with user ID: {}", dto.getUserId());

        return MemberMapper.toDto(savedEntity);
    }

    /**
     *  로그인
     */
    @Transactional
    public LoginDto memberLogin(String userId, String password) {
        MemberEntity memberEntity = memberRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("아이디를 확인해주세요"));

        if (!memberEntity.getIsActive()) {
            throw new RuntimeException("아이디가 존재하지 않습니다.");
        }

        if (!passwordEncoder.matches(password, memberEntity.getPassword())) {
            throw new RuntimeException("비밀번호를 확인해주세요");
        }

        LoginDto loginDto = MemberMapper.toLoginDto(memberEntity);

        String accessToken = jwtTokenProvider.generateAccessToken(loginDto);
        String refreshToken = jwtTokenProvider.generateRefreshToken(loginDto);

        // 기존 리프레시 토큰을 확인하여 업데이트 또는 새로 저장
        TokenEntity tokenEntity = tokenRepository.findByUserId(userId)
                .map(token -> {
                    token.setRefreshToken(refreshToken);
                    return token;
                })
                .orElseGet(() -> TokenEntity.builder()
                        .userId(userId)
                        .refreshToken(refreshToken)
                        .build());

        tokenRepository.save(tokenEntity);
        // 토큰과 사용자 정보를 포함한 LoginDto 생성
        return MemberMapper.createTokenLoginResponseDto(loginDto, accessToken, refreshToken, userId);
    }

    /**
     *  회원 정보 가져오기
     */
    public MemberInfoDto myInfoMember(String userId) {
        MemberEntity member = memberRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        log.info("Service 회원정보 ID : {}" , userId);
        return MemberMapper.toInfoDto(member);
    }

    /**
     *  회원 탈퇴
     */
    @Transactional
    public void deleteMemberById(Long id) {
        MemberEntity member = memberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("아이디가 존재하지 않습니다. 확인해주세요"));
        log.info("Service 회원 탈퇴 id : {}" , id);

        List<ListEntity> listEntityList = listRepository.findByMember(member);
        for (ListEntity list : listEntityList) {
            list.deactivate();
        }
        log.info("삭제된 게시글 : {} ", listEntityList.stream().map(ListEntity::getId).collect(Collectors.toList()));
        listRepository.saveAll(listEntityList);

        member.deactivate();
        memberRepository.save(member);
        log.info("Deactivated member with user ID");
    }


    /**
     * 회원 수정
     */
    @Transactional
    public void updateMember(Long id, MemberUpdateRequestDto dto) {
        String hashedPassword = passwordEncoder.encode(dto.getPassword());

        Optional<MemberEntity> optionalMember = memberRepository.findById(id);
        MemberEntity member = optionalMember.orElseThrow(() -> new RuntimeException("회원이 존재하지 않습니다."));

        if(!dto.getPassword().isEmpty()) {
            member.setPassword(hashedPassword);
        }
        if(!dto.getMemo().isEmpty()) {
            member.setMemo(dto.getMemo());
        }

        memberRepository.save(member);
    }

    public boolean userIdCheck(String userId) {
        if (userId == null || userId.isEmpty()) {
            throw new IllegalArgumentException("아이디를 입력해주세요");
        }
        return memberRepository.findByUserId(userId).isEmpty();
    }
}
