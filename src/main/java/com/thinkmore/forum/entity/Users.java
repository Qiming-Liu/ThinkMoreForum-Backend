package com.thinkmore.forum.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "users")
public class Users {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "username", nullable = false, length = 20)
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "profile_img_url")
    private String profileImgUrl;

    @ManyToOne(optional = false)
    @JoinColumn(name = "role_id", nullable = false)
    private Roles role;

    @Column(name = "last_login_timestamp", nullable = false)
    private OffsetDateTime lastLoginTimestamp;

    @Column(name = "create_timestamp", nullable = false)
    private OffsetDateTime createTimestamp;
}