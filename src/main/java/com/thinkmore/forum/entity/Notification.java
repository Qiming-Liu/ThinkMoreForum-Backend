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
@Table(name = "notification")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "trigger_users_id", nullable = false)
    private Users triggerUsers;

    @ManyToOne(optional = false)
    @JoinColumn(name = "notify_users_id", nullable = false)
    private Users notifyUsers;

    @Column(name = "context", nullable = false)
    private String context;

    @Column(name = "viewed")
    private Boolean viewed = false;

    @Column(name = "create_timestamp")
    private OffsetDateTime createTimestamp;
}