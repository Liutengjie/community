package com.liu.community.dto;

import lombok.Data;

@Data
public class CommentCreateDTO {
    private Long parenId;
    private String content;
    private Integer type;
}
