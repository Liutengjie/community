package com.liu.community.controller;

import com.liu.community.dto.CommentCreateDTO;
import com.liu.community.dto.CommentDTO;
import com.liu.community.dto.ResultDTO;
import com.liu.community.enums.CommentTypeEnum;
import com.liu.community.exception.CustomizeErrorCode;
import com.liu.community.mapper.CommentMapper;
import com.liu.community.model.Comment;
import com.liu.community.model.User;
import com.liu.community.service.CommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller

//回复框和二级评论
public class CommentController {


    @Autowired
    CommentService commentService;

   //反应回复框
    @ResponseBody
    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    public ResultDTO post( @RequestBody CommentCreateDTO commentCreateDTO
            , HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        Comment coment=new Comment();

        if(user==null){
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        }

        if (commentCreateDTO==null|| StringUtils.isBlank(commentCreateDTO.getContent())){
            return  ResultDTO.errorOf(CustomizeErrorCode.CONTENT_IS_EMPTY);
        }

    coment.setParentId(commentCreateDTO.getParenId());
    coment.setContent(commentCreateDTO.getContent());
    coment.setType(commentCreateDTO.getType());
    coment.setGmtcreate(System.currentTimeMillis());
    coment.setGmtmodified(System.currentTimeMillis());
    coment.setCommentator(user.getId());
    coment.setLikeCount(0L);
    commentService.insert(coment,user);//判断是回复还是评论
    return ResultDTO.okof();
    }

    @ResponseBody
    @RequestMapping(value = "/comment/{id}",method = RequestMethod.GET)
    public ResultDTO comments(@PathVariable(name="id") Long id){
        List<CommentDTO> commentDTOS = commentService.listByQuestionId(id, CommentTypeEnum.COMMENT);
        return ResultDTO.okof(commentDTOS);
    }
}
