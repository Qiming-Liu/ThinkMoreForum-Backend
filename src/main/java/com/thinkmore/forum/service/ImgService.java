package com.thinkmore.forum.service;

import com.thinkmore.forum.configuration.StaticConfig;
import com.thinkmore.forum.entity.Img;
import com.thinkmore.forum.repository.ImgRepository;
import io.minio.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImgService {
    private final ImgRepository imgRepository;
    private final MinioClient minioClient;

    @Value("${minio.url}")
    private String minioUrl;

    @Transactional
    public Img upload(MultipartFile img) throws Exception {
        byte[] imgBytes = img.getBytes();
        String md5 = DigestUtils.md5Hex(imgBytes);

        // check
        Optional<Img> image = imgRepository.findByMd5(md5);
        if (image.isPresent()) {
            return image.get();
        }

        // put
        String fileName = img.getOriginalFilename();
        if (fileName == null) {
            throw new RuntimeException();
        }
        fileName = md5 + (fileName.endsWith(".png") ? ".png" : ".jpg");

        minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(StaticConfig.BucketName)
                        .object(fileName)
                        .stream(new ByteArrayInputStream(imgBytes), imgBytes.length, -1)
                        .contentType(fileName.endsWith(".png") ? "image/png" : "image/jpeg")
                        .build());

        // set
        Img theImg = new Img();
        theImg.setUrl(minioUrl + "/" + StaticConfig.BucketName + "/" + fileName);
        theImg.setMd5(md5);
        imgRepository.save(theImg);

        return theImg;
    }
}
