package com.thinkmore.forum.controller.v1;

import com.thinkmore.forum.entity.Img;
import com.thinkmore.forum.service.ImgService;
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
    public ResponseEntity<Img> upload(@RequestBody MultipartFile img) throws Exception {
        return ResponseEntity.ok(imgService.upload(img));
    }
}
