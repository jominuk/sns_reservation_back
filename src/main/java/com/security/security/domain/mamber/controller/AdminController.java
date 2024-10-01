package com.security.security.domain.mamber.controller;

import com.security.security.domain.mamber.dto.admin.AdminDto;
import com.security.security.domain.mamber.service.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    /**
     *  회원 목록
     */
    @GetMapping("/member")
    public ResponseEntity<Page<AdminDto.memberList>> adminMember(
            @RequestParam(required = false, defaultValue = "10", value = "pageSize") int size,
            @PageableDefault(page = 0) Pageable pageable) {

        Pageable pageNation = PageRequest.of(pageable.getPageNumber(), size, pageable.getSort());

        Page<AdminDto.memberList> memberPage = adminService.adminMember(pageNation);

        return ResponseEntity.ok(memberPage);
    }

}
