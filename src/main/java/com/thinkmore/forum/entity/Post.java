package com.thinkmore.forum.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@DynamicUpdate
@DynamicInsert
@Table(name = "post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "post_users_id", nullable = false)
    private Users postUsers;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "head_img_url")
    private String headImgUrl;

    @Column(name = "title", nullable = false, length = 100)
    private String title;

    @Column(name = "context", nullable = false, length = 65535)
    private String context;

    @Column(name = "view_count")
    private Integer viewCount;

    @Column(name = "follow_count")
    private Integer followCount;

    @Column(name = "comment_count")
    private Integer commentCount;

    @Column(name = "visibility")
    private Boolean visibility;

    @Column(name = "create_timestamp")
    private OffsetDateTime createTimestamp;
}