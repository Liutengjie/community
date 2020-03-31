package com.liu.community.mapper;

import com.liu.community.model.Comment;
import com.liu.community.model.CommentExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CommentExtMapper {
    int incCommentCount(Comment comment);
}