package com.thinkmore.forum.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "notification")
public class Notification {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "users_id", nullable = false)
    private Users users;

    @Column(name = "img_url", nullable = false)
    private String imgUrl;

    @Column(name = "context", nullable = false)
    private String context;

    @Column(name = "viewed", nullable = false)
    private Boolean viewed = false;

    @Column(name = "create_timestamp", nullable = false)
    private OffsetDateTime createTimestamp;
}