package com.thinkmore.forum.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Data
@Getter
@Setter
@NonNull
public class PasswordPutDto {

    private String oldPassword;
    private String newPassword;
}
