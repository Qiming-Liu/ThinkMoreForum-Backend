package com.thinkmore.forum.service;

import com.thinkmore.forum.dto.img.ImgGetDto;
import com.thinkmore.forum.mapper.ImgMapper;
import com.thinkmore.forum.repository.ImgRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ImgService {
    private final ImgRepository imgRepo;

    private final ImgMapper imgMapper;

    public List<ImgGetDto> getAllImgs() {

        return imgRepo.findAll().stream()
                .map(imgMapper::fromEntity)
                .collect(Collectors.toList());
    }


    public List<ImgGetDto> getImgByName(String img_name) {

        return imgRepo.findByImgName(img_name).stream()
                .map(imgMapper::fromEntity)
                .collect(Collectors.toList());
    }
}
