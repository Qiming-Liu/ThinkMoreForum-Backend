package com.thinkmore.forum.entity.rabbitmq;

import java.io.Serializable;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ResetPasswordEmailMessage implements Serializable {
    private final String email;
}

