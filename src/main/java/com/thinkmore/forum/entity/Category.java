package com.thinkmore.forum.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "pin_post_id")
    private Post pinPost;

    @ManyToOne
    @JoinColumn(name = "profile_img_id")
    private Img profileImg;

    @Column(name = "color", nullable = false, length = 7)
    private String color;

    @Column(name = "title", nullable = false, length = 20)
    private String title;

    @Column(name = "description", length = 60)
    private String description;

    @Column(name = "post_count", nullable = false)
    private Integer postCount;

    @Column(name = "sort_order", nullable = false)
    private Integer sortOrder;
}