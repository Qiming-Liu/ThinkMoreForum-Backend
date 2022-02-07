package com.thinkmore.forum.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Data
@Getter
@Setter
@NonNull
public class UsernamePutDto {

    private String oldUsername;
    private String newUsername;
}
