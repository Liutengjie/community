package com.liu.community.service;

import com.liu.community.dto.CommentDTO;
import com.liu.community.enums.CommentTypeEnum;
import com.liu.community.enums.NotificationStatusEnm;
import com.liu.community.enums.NotificationTypeEnum;
import com.liu.community.exception.CustomizeErrorCode;
import com.liu.community.exception.CustomizeException;
import com.liu.community.mapper.*;

import com.liu.community.model.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionExtMapper questionExtMapper;

    @Autowired
    private CommentExtMapper commentExtMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private NottificationMapper nottificationMapper;
    //回复问题或者回复评论
    @Transactional
    public void insert(Comment coment,User user) {
        if (coment.getParentId()==null ||coment.getParentId()==0){
        throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }
        if (coment.getType()==null || !CommentTypeEnum.isExist(coment.getType())){
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }
        if(coment.getType()==CommentTypeEnum.COMMENT.getType()){
        //回复评论
            //根据id查评论
            Comment dbComment=commentMapper.selectByPrimaryKey(coment.getParentId());
            if(dbComment==null){
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);

            }
            //根据id查文章
            Question question = questionMapper.selectByPrimaryKey(dbComment.getParentId());
            if (question==null){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            commentMapper.insert(coment);//增加
            //增加评论数
            Comment parentcomment = new Comment();
            parentcomment.setId(coment.getParentId());
            parentcomment.setCommentcount(1);
            commentExtMapper.incCommentCount(parentcomment);//增加回复数
            //创建通知
            createNotify(coment, dbComment.getCommentator(), user.getName(),question.getTitle(),NotificationTypeEnum.REPLY_COMMENT,question.getId());
        }else{
            //回复问题
            //根据id查文章
            Question question = questionMapper.selectByPrimaryKey(coment.getParentId());
            if (question==null){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            coment.setCommentcount(0);
            commentMapper.insert(coment);//增加
           question.setCommentCont(1);
            questionExtMapper.incComment(question);//更新回复数
            //创建通知
            createNotify(coment,question.getCreator(), user.getName(),question.getTitle(),NotificationTypeEnum.REPLY_QUESTION,question.getId());
        }
    }
    //创建通知
    private void createNotify(Comment coment, Long receiver,String name,String outerTitle, NotificationTypeEnum replyComment,Long outerId) {
       if (receiver==coment.getCommentator()){
           return;
       }

        Nottification nottification = new Nottification();
        nottification.setGmtcreate(System.currentTimeMillis());
        nottification.setOuterid(outerId);//被通知的人文章id
        nottification.setNotifier(coment.getCommentator());//用户id
        nottification.setType(replyComment.getType());
        nottification.setStatus(NotificationStatusEnm.UNREAD.getStatus());
        nottification.setReceiver(receiver);//被通知的人用户id
        nottification.setNotiferName(name);//通知人的名字
        nottification.setOuterTitle(outerTitle);//被通知人的文章
        nottificationMapper.insert(nottification);
    }

    //返回评论
    public List<CommentDTO> listByQuestionId(Long id, CommentTypeEnum type) {
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria().andParentIdEqualTo(id)
                .andTypeEqualTo(type.getType());
        commentExample.setOrderByClause("gmtcreate desc");
        //所有评论
        List<Comment> comments = commentMapper.selectByExample(commentExample);
        if (comments.size()==0){
            return  new ArrayList<>();
        }
        //获取去重评论人
        Set<Long> collect = comments.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());
        List<Long> userIds = new ArrayList<>();
        userIds.addAll(collect);

        //获取评论人user转换为MAp
        UserExample userExample = new UserExample();
        userExample.createCriteria().andIdIn(userIds);
        List<User> users = userMapper.selectByExample(userExample);

        //key 为user.getId()
        Map<Long,User> userMap=users.stream().collect(Collectors.toMap(user -> user.getId(),user -> user));

        //转换comment为commentDTO
            List<CommentDTO> commentDTOS=comments.stream().map(comment -> {
                CommentDTO commentDTO =new CommentDTO();
                BeanUtils.copyProperties(comment,commentDTO);
                commentDTO.setUser(userMap.get(comment.getCommentator()));
                return  commentDTO;
            }).collect(Collectors.toList());

        return  commentDTOS;

    }
}
