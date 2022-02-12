package com.thinkmore.forum.service;

import com.thinkmore.forum.configuration.Config;
import com.thinkmore.forum.dto.img.ImgGetDto;
import com.thinkmore.forum.mapper.ImgMapper;
import com.thinkmore.forum.repository.ImgRepository;
import com.thinkmore.forum.util.Util;
import io.minio.*;
import io.minio.http.Method;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImgService {
    private final ImgRepository imgRepo;
    private final ImgMapper imgMapper;

    public List<ImgGetDto> getAllImg() {
        return imgRepo.findAll().stream()
                .map(imgMapper::fromEntity)
                .collect(Collectors.toList());
    }

    public List<ImgGetDto> getImgByName(String img_name) {
        return imgRepo.findByImgUrl(img_name).stream()
                .map(imgMapper::fromEntity)
                .collect(Collectors.toList());
    }

    public String uploadImg(InputStream imgStream, String md5) throws Exception {
        // check




        // put
        String BucketName = "img";
        boolean found = Util.minioClient.bucketExists(BucketExistsArgs.builder().bucket(BucketName).build());
        if (!found) {
            Util.minioClient.makeBucket(MakeBucketArgs.builder().bucket(BucketName).build());
        }
        Util.minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(BucketName)
                        .object(md5)
                        .stream(imgStream, -1, 5 * 1024 * 1024)
                        .contentType("image/jpg")
                        .build());

        // get url
        Map<String, String> reqParams = new HashMap<>();
        reqParams.put("response-content-type", "image/jpg");

        return Util.minioClient.getPresignedObjectUrl(
                GetPresignedObjectUrlArgs.builder()
                        .method(Method.GET)
                        .bucket(BucketName)
                        .object(md5)
                        .expiry(36525, TimeUnit.DAYS)
                        .extraQueryParams(reqParams)
                        .build());
    }
}
