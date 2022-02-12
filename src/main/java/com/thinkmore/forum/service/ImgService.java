package com.thinkmore.forum.service;

import com.thinkmore.forum.dto.img.ImgGetDto;
import com.thinkmore.forum.entity.Img;
import com.thinkmore.forum.mapper.ImgMapper;
import com.thinkmore.forum.repository.ImgRepository;
import com.thinkmore.forum.util.Util;
import io.minio.*;
import io.minio.http.Method;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImgService {
    private final ImgRepository imgRepo;
    private final ImgMapper imgMapper;

    @Transactional
    public List<ImgGetDto> getAllImg() {
        return imgRepo.findAll().stream()
                .map(imgMapper::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public ImgGetDto getImgById(UUID id) {
        return imgMapper.fromEntity(imgRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Img id not found: " + id)));
    }

    @Transactional
    public ImgGetDto getImgByHash(String md5) {
        return imgMapper.fromEntity(imgRepo.findByHash(md5)
                .orElseThrow(() -> new RuntimeException("Img md5 not found: " + md5)));
    }

    @Transactional
    public String uploadImg(InputStream imgStream, String md5) throws Exception {
        // check
        Optional<Img> image = imgRepo.findByHash(md5);
        if (image.isPresent()) {
            return image.get().getUrl();
        }

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

        String url = Util.minioClient.getPresignedObjectUrl(
                GetPresignedObjectUrlArgs.builder()
                        .method(Method.GET)
                        .bucket(BucketName)
                        .object(md5)
                        .expiry(7, TimeUnit.DAYS)
                        .extraQueryParams(reqParams)
                        .build());

        // set
        Img img = new Img();
        img.setUrl(url);
        img.setHash(md5);
        imgRepo.save(img);

        return url;
    }
}
