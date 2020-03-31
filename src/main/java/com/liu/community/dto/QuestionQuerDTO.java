package com.liu.community.dto;

import lombok.Data;

@Data
public class QuestionQuerDTO {
    private String search;
    private Integer page;
    private Integer size;
}
