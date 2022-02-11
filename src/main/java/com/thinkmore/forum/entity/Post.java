package com.thinkmore.forum.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "post")
public class Post {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "post_users_id", nullable = false)
    private Users postUsers;

    @ManyToOne(optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "head_img_id")
    private Img headImg;

    @Column(name = "title", nullable = false, length = 60)
    private String title;

    @Column(name = "context", nullable = false, length = 65535)
    private String context;

    @Column(name = "view_count", nullable = false)
    private Integer viewCount;

    @Column(name = "follow_count", nullable = false)
    private Integer followCount;

    @Column(name = "comment_count", nullable = false)
    private Integer commentCount;

    @Column(name = "visibility", nullable = false)
    private Boolean visibility = false;

    @Column(name = "create_timestamp", nullable = false)
    private OffsetDateTime createTimestamp;
}