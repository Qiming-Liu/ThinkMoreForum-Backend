package com.thinkmore.forum.service;

import com.thinkmore.forum.configuration.Config;
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
    public Img uploadImg(InputStream imgStream, String md5) throws Exception {
        String BucketName = "image";
        String fileName = md5 + ".png";
        boolean found = Util.minioClient.bucketExists(BucketExistsArgs.builder().bucket(BucketName).build());
        if (!found) {
            Util.minioClient.makeBucket(MakeBucketArgs.builder().bucket(BucketName).build());
        }

        // check
        Optional<Img> image = imgRepo.findByHash(md5);
        if (image.isPresent()) {
            return image.get();
        }

        // put
        Util.minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(BucketName)
                        .object(fileName)
                        .stream(imgStream, -1, 5 * 1024 * 1024)
                        .contentType("image/png")
                        .build());

        String policyJson = "{\"Version\":\"2012-10-17\",\"Statement\":[{\"Effect\":\"Allow\",\"Principal\":{\"AWS\":[\"*\"]},\"Action\":[\"s3:ListBucketMultipartUploads\",\"s3:GetBucketLocation\",\"s3:ListBucket\"],\"Resource\":[\"arn:aws:s3:::image\"]},{\"Effect\":\"Allow\",\"Principal\":{\"AWS\":[\"*\"]},\"Action\":[\"s3:AbortMultipartUpload\",\"s3:DeleteObject\",\"s3:GetObject\",\"s3:ListMultipartUploadParts\",\"s3:PutObject\"],\"Resource\":[\"arn:aws:s3:::image/*\"]}]}";
        Util.minioClient.setBucketPolicy(
                SetBucketPolicyArgs.builder().bucket(BucketName).config(policyJson).build());

        // set
        Img img = new Img();
        img.setUrl(Config.OssUrl + BucketName + "/" + fileName);
        img.setHash(md5);
        imgRepo.save(img);

        return img;
    }
}
