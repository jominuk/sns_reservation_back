package com.security.security.domain.list.controller;

import com.security.security.domain.list.dto.ListDto;
import com.security.security.domain.list.service.ListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/list")
public class ListController {

    private final ListService listService;

    @PostMapping("/post")
    public ResponseEntity<ListDto> listPost(
            @RequestPart(value = "dto") ListDto dto,
            @RequestPart(value = "image", required = false) MultipartFile image
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(listService.listPost(dto, image));
    }

    @GetMapping("/get")
    public ResponseEntity<List<ListDto>> listGet(
            @RequestParam(name = "userId") String userId
    ) {
        List<ListDto> listDtos = listService.listGet(userId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(listDtos);
    }
}
