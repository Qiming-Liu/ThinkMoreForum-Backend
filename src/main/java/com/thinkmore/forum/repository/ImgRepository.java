package com.thinkmore.forum.repository;

import com.thinkmore.forum.entity.Img;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ImgRepository extends JpaRepository<Img, UUID> {
    List<Img> findByImgUrl(String imgName);

}
