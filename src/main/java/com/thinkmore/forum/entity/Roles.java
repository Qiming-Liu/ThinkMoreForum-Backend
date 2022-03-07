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
@Table(name = "roles")
public class Roles {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "role_name", nullable = false, length = 20)
    private String roleName;

    @Column(name = "permission", nullable = false)
    private String permission;
}