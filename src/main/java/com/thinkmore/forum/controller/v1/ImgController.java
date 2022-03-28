package com.thinkmore.forum.controller.v1;

import com.thinkmore.forum.entity.Img;
import com.thinkmore.forum.service.ImgService;
import com.thinkmore.forum.util.Util;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(path = "/v1/img")
@RequiredArgsConstructor
public class ImgController {

    private final ImgService imgService;

    @PostMapping(path = "/upload")
    public ResponseEntity<Img> upload(@RequestParam MultipartFile img) throws Exception {
        Util.checkPermission("uploadImg");
        return ResponseEntity.ok(imgService.upload(img));
    }
}
