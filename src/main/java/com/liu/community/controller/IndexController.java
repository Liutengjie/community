package com.liu.community.controller;

import com.liu.community.dto.Pagination;
import com.liu.community.dto.QuestionDTO;

import com.liu.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.liu.community.service.QuestionService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
//首页
@Controller
public class IndexController {


    @Autowired
  private QuestionService questionService;

   @GetMapping("/")
    public String hello(HttpServletRequest request, Model model, @RequestParam(name="page",defaultValue = "1")Integer page
   ,@RequestParam(name="size",defaultValue = "10")Integer size
   ,@RequestParam(name = "search",required = false) String search){
        //查询所有
       Pagination pagination=questionService.list(search,page,size);
        model.addAttribute("search",search);
       //传递文章
        model.addAttribute("pagination",pagination);
        //分页



        return "index";
    }
}
