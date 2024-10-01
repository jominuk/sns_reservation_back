package com.security.security.domain.mamber.controller;

import com.security.security.domain.mamber.dto.LoginDto;
import com.security.security.domain.mamber.dto.MemberDto;
import com.security.security.domain.mamber.dto.request.MemberUpdateRequestDto;
import com.security.security.domain.mamber.dto.response.MemberInfoDto;
import com.security.security.domain.mamber.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    /**
     * 사용자 추가
     * */
    @PostMapping("/save")
    public ResponseEntity<MemberDto> memberSave(
            @RequestBody MemberDto dto
    ) {
        log.info("Received request to save member with details: {}", dto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(memberService.memberSave(dto));
    }

    /**
     * 로그인
     */
    @PostMapping("/")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
        try {
            LoginDto responseDto = memberService.memberLogin(loginDto.getUserId(), loginDto.getPassword());

            // ROLE에 권한을 체크
            List<GrantedAuthority> authorities = new ArrayList<>();
            if (responseDto.getRole() != null) {
                authorities.add(new SimpleGrantedAuthority("ROLE_" + responseDto.getRole().name()));
            } else {
                // 기본 권한을 설정하거나 로그를 남깁니다.
                authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
                log.warn("User role is null for user: {}", responseDto.getUserId());
            }

            Authentication authentication = new UsernamePasswordAuthenticationToken(responseDto, null, authorities);

            SecurityContextHolder.getContext().setAuthentication(authentication);

            return ResponseEntity.ok(responseDto);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonMap("error", e.getMessage()));
        }
    }

    /**
     * 회원 정보
     */
    //@PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/myInfo")
    public ResponseEntity<MemberInfoDto> myInfoMember(@RequestParam(name = "userId") String userId) {
        log.info("정보 진입 유저 ID: {}", userId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(memberService.myInfoMember(userId));
    }

    /**
     *  회원 탈퇴
     */
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteMember(@RequestParam(name = "id") Long id) {
        memberService.deleteMemberById(id);
        log.info("삭제 id번호 : {}" , id);
        return ResponseEntity.ok("회원탈퇴가 성공적으로 이루어졌습니다. 감사합니다!");
    }

    /**
     * 회원 수정
     */
    @PatchMapping("/update")
    public ResponseEntity<String> updateMember(
            @RequestParam(name = "id") Long id,
            @RequestBody MemberUpdateRequestDto dto
    ) {
        log.info("id 번호는 ? : {}" , id);

        memberService.updateMember(id, dto);
        return ResponseEntity.ok("회원 정보가 성공적으로 업데이트되었습니다. 다시 로그인을 진행해 주세요");
    }

    /**
     *  userId 중복체크
     */
    @GetMapping("/userId")
    public ResponseEntity<String> userIdCheck(@RequestParam(name = "userId") String userId) {
        boolean member = memberService.userIdCheck(userId);
        if(member) {
            return ResponseEntity.ok("사용 가능한 id 입니다.");
        }else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 사용 중인 아이디입니다.");
        }
    }
}
