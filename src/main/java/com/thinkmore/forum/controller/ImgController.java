package com.thinkmore.forum.controller;

import com.thinkmore.forum.dto.img.ImgGetDto;
import com.thinkmore.forum.service.ImgService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping(path = "/v1/img")
@RequiredArgsConstructor
public class ImgController {

    private final ImgService imgService;

    @GetMapping(path = "/get_img")
    public ResponseEntity<List<ImgGetDto>> findAll() {
        List<ImgGetDto> imgList = imgService.getAllImg();
        return ResponseEntity.ok(imgList);
    }

    @GetMapping(path = "/get_img/{img_name}")
    public ResponseEntity<List<ImgGetDto>> findAll(@PathVariable("img_name") String img_name) {
        List<ImgGetDto> imgList = imgService.getImgByName(img_name);
        return ResponseEntity.ok(imgList);
    }

    @PostMapping(path = "/upload")
    public ResponseEntity<String> upload(@RequestParam MultipartFile file, @RequestParam String md5) throws Exception {
        byte [] byteArr = file.getBytes();
        InputStream inputStream = new ByteArrayInputStream(byteArr);
        return ResponseEntity.ok(imgService.uploadImg(inputStream, md5));
    }
}
