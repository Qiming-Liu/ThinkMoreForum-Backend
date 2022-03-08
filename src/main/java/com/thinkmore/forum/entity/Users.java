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
@Table(name = "users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "username", nullable = false, length = 20)
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "email", nullable = false, length = 255)
    private String email;

    @Column(name = "head_img_url")
    private String headImgUrl;

    @Column(name = "profile_img_url")
    private String profileImgUrl;

    @ManyToOne(optional = false)
    @JoinColumn(name = "role_id", nullable = false)
    private Roles role;

    @Column(name = "last_login_timestamp")
    private OffsetDateTime lastLoginTimestamp;

    @Column(name = "create_timestamp")
    private OffsetDateTime createTimestamp;
}