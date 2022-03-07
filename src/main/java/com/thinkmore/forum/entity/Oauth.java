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
@Table(name = "oauth")
public class Oauth {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "users_id", nullable = false)
    private Users users;

    @Column(name = "oauth_type", nullable = false, length = 20)
    private String oauthType;

    @Column(name = "openid", nullable = false)
    private String openid;
}