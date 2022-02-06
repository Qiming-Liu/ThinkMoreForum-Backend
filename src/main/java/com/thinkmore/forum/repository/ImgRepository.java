package com.thinkmore.forum.repository;

import com.thinkmore.forum.entity.Img;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImgRepository extends JpaRepository<Img, Long> {
    List<Img> findByImgName(String imgName);


}
