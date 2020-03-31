package com.liu.community.dto;

import com.liu.community.model.User;
import lombok.Data;

@Data
public class CommentDTO {
    private Long id;


    private Long parentId;


    private String content;


    private Long commentator;

    private Long gmtcreate;

    private Long gmtmodified;

    private Long likeCount;
    private User user;
    private Integer commentcount;
}
