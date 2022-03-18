package com.thinkmore.forum.message;

import java.io.Serializable;
import java.util.UUID;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class VerificationEmailMessage implements Serializable {
    private final UUID usersId;
    private final String new_email;
}

