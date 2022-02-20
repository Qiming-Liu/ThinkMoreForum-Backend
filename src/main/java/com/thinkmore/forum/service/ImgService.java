package com.thinkmore.forum.service;

import com.thinkmore.forum.configuration.Config;
import com.thinkmore.forum.dto.img.ImgGetDto;
import com.thinkmore.forum.entity.Img;
import com.thinkmore.forum.mapper.ImgMapper;
import com.thinkmore.forum.repository.ImgRepository;
import com.thinkmore.forum.util.Singleton;
import io.minio.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImgService {
    private final ImgRepository imgRepository;
    private final ImgMapper imgMapper;

    @Transactional
    public List<ImgGetDto> getAllImg() {
        return imgRepository.findAll().stream()
                .map(imgMapper::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public ImgGetDto getImgById(UUID id) {
        return imgMapper.fromEntity(imgRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Img id not found: " + id)));
    }

    @Transactional
    public ImgGetDto getImgByHash(String md5) {
        return imgMapper.fromEntity(imgRepository.findByHash(md5)
                .orElseThrow(() -> new RuntimeException("Img md5 not found: " + md5)));
    }

    @Transactional
    public Img uploadImg(InputStream imgStream, String md5) throws Exception {
        // check
        Optional<Img> image = imgRepository.findByHash(md5);
        if (image.isPresent()) {
            return image.get();
        }

        // put
        String fileName = md5 + ".png";
        Singleton.minioClient().putObject(
                PutObjectArgs.builder()
                        .bucket(Config.BucketName)
                        .object(fileName)
                        .stream(imgStream, -1, 5 * 1024 * 1024)
                        .contentType("image/png")
                        .build());

        // set
        Img img = new Img();
        img.setUrl(Config.OssUrl + Config.BucketName + "/" + fileName);
        img.setHash(md5);
        imgRepository.save(img);

        return img;
    }
}
