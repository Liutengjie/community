package com.liu.community.controller;

import com.liu.community.dto.CommentCreateDTO;
import com.liu.community.dto.CommentDTO;
import com.liu.community.dto.QuestionDTO;
import com.liu.community.enums.CommentTypeEnum;
import com.liu.community.model.Question;
import com.liu.community.service.CommentService;
import com.liu.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

//阅读问题和评论
@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private CommentService commentService;


    @GetMapping("/question/{id}")
    private String question(@PathVariable(name="id")Long id
    , Model model){
        QuestionDTO questions = questionService.getByid(id);
        List<QuestionDTO> ralatedQuestions=questionService.selectRelatec(questions);
        //返回评论
        List<CommentDTO> commentDTOS=commentService.listByQuestionId(id, CommentTypeEnum.QUESTION);
        questionService.inView(id);////回复数加1
        //显示问题和用户
        model.addAttribute("question",questions);
        model.addAttribute("comments",commentDTOS);
        model.addAttribute("ralatedQuestions",ralatedQuestions);
        return "question";
    }
}
