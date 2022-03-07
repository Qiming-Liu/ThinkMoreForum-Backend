package com.thinkmore.forum.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@Setter
@DynamicUpdate
@DynamicInsert
@Table(name = "img")
public class Img {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "url", nullable = false)
    private String url;

    @Column(name = "md5", nullable = false)
    private String md5;
}
