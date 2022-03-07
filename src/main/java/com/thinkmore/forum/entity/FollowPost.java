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
@Table(name = "follow_post")
public class FollowPost {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "users_id", nullable = false)
    private Users users;

    @ManyToOne(optional = false)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @Column(name = "create_timestamp")
    private OffsetDateTime createTimestamp;
}