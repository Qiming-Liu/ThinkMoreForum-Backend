package com.thinkmore.forum.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Entity
//@Getter
//@Setter
@Table(name = "img")
public class Img {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "img_name", nullable = false, length = 260)
    private String imgName;

    @Column(name = "img_hash", nullable = false)
    private String imgHash;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

    public String getImgHash() {
        return imgHash;
    }

    public void setImgHash(String imgHash) {
        this.imgHash = imgHash;
    }
}