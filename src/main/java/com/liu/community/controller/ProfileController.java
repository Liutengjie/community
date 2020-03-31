package com.liu.community.controller;

import com.liu.community.dto.Pagination;
import com.liu.community.model.User;
import com.liu.community.service.NottificationService;
import com.liu.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
//查看我的问题
@Controller
public class ProfileController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private NottificationService nottificationService;

    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name = "action") String action
            ,HttpServletRequest request
            , Model model
            ,@RequestParam(name="page",defaultValue = "1")Integer page
            ,@RequestParam(name="size",defaultValue = "10")Integer size){

       //获取拦截器的值
        User user = (User)request.getSession().getAttribute("user");

        if(user==null){
            model.addAttribute("error","用户未登陆");
            return "redirect:/";

        }


        if ("questions".equals(action)){
            model.addAttribute("section","questions");
            model.addAttribute("sectionName","我的提问");
            Pagination pagination = questionService.list(user.getId(), page, size);
            model.addAttribute("pagination",pagination);
        }
        else if("repies".equals(action)){
            Pagination paginationDTO=nottificationService.list(user.getId(),page,size);//展示回复
           Long unreadCount=nottificationService.unreadCount(user.getId()); //最新未回复数
            model.addAttribute("section","repies");
            model.addAttribute("sectionName","我的回复");
            model.addAttribute("Notification",paginationDTO);
            model.addAttribute("unreadCount",unreadCount);
            model.addAttribute("pagination",paginationDTO);
        }


        return "profile";
    }
}
