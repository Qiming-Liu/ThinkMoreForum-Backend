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

    @Column(name = "url", nullable = false, length = 260)
    private String url;

<<<<<<< Updated upstream
    @Column(name = "hash", nullable = false)
    private String hash;
=======
    @Column(name = "img_hash", nullable = false)
    private String imgHash;

>>>>>>> Stashed changes
}