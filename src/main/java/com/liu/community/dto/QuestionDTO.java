package com.liu.community.dto;

import com.liu.community.model.User;
import lombok.Data;

@Data
public class QuestionDTO {
    private Long id;
    private String title;
    private String description;
    private String tag;
    private Long gmtcreate;
    private Long gmtmodified;
    private Long creator;
    private Integer viewcont;
    private Integer likecount;
    private Integer commentCont;
    private User user;
}
