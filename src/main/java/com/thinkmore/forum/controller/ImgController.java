package com.thinkmore.forum.controller;

import com.thinkmore.forum.dto.img.ImgGetDto;
import com.thinkmore.forum.service.ImgService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/img")
@RequiredArgsConstructor
public class ImgController {

    private final ImgService imgService;

    @GetMapping(path = "/get_img")
    public ResponseEntity<List<ImgGetDto>> findAll() {
        List<ImgGetDto> imgList = imgService.getAllImgs();
        return ResponseEntity.ok(imgList);
    }

    @GetMapping(path = "/get_img/{img_name}")
    public ResponseEntity<List<ImgGetDto>> findAll(@PathVariable("img_name") String img_name) {
        List<ImgGetDto> imgList = imgService.getImgByName(img_name);
        return ResponseEntity.ok(imgList);
    }
}
