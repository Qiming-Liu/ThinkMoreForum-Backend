package com.thinkmore.forum.entity;

import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@Setter
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

}