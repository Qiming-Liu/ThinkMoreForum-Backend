package com.thinkmore.forum.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "post_comment")
public class PostComment {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "post_users_id", nullable = false)
    private Users postUsers;

    @ManyToOne(optional = false)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @ManyToOne
    @JoinColumn(name = "parent_comment_id")
    private PostComment parentComment;

    @Column(name = "context", length = 65535)
    private String context;

    @Column(name = "visibility", nullable = false)
    private Boolean visibility = false;

    @Column(name = "create_timestamp", nullable = false)
    private OffsetDateTime createTimestamp;
}