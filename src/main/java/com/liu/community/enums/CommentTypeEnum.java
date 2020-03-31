package com.liu.community.enums;

public enum CommentTypeEnum {
    QUESTION(1),//回复问题
    COMMENT(2);//回复评论
    private Integer type;

    CommentTypeEnum(Integer type) {
        this.type = type;
    }

    public static boolean isExist(Integer type) {
        for (CommentTypeEnum commentTypeEnum: CommentTypeEnum.values()){
            if(commentTypeEnum.getType()==type){
                return true;
            }
        }
        return false;
    }

    public Integer getType() {
        return type;
    }
}
