package com.thinkmore.forum.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class ForumErrorDto {
    private  String message;
    private  String details;
}
