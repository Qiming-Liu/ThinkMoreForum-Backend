package com.thinkmore.forum.repository;

import com.thinkmore.forum.entity.Img;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ImgRepository extends JpaRepository<Img, UUID> {
    Optional<Img> findByMd5(String hash);
}
