package com.liu.community.mapper;

import com.liu.community.dto.QuestionQuerDTO;
import com.liu.community.model.Comment;
import com.liu.community.model.Question;
import com.liu.community.model.QuestionExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface QuestionExtMapper {
   Integer countBsySearch(QuestionQuerDTO questionQuerDTO);//查总条数

    int incView(Question record);//增加浏览数
    int incComment(Question question);//增加回复数
    List<Question> selectRelated(Question question);//标签类型的内容

    List<Question> selectBySearch(QuestionQuerDTO questionQuerDTO);
}