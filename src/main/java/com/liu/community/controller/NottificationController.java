package com.liu.community.controller;

import com.liu.community.dto.NottificationDTO;
import com.liu.community.dto.Pagination;
import com.liu.community.enums.NotificationTypeEnum;
import com.liu.community.mapper.NottificationMapper;
import com.liu.community.model.User;
import com.liu.community.service.NottificationService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
@Controller
//通知
public class NottificationController {

    @Autowired
    private NottificationService nottificationService;

    @GetMapping("/notification/{id}")
    public String profile(@PathVariable(name = "id") Long id, HttpServletRequest request
    , Model model){

        //获取拦截器的值
        User user = (User)request.getSession().getAttribute("user");

        if(user==null){
            model.addAttribute("error","用户未登陆");
            return "redirect:/";

        }

        NottificationDTO nottificationDTO=nottificationService.read(id,user);

        if (NotificationTypeEnum.REPLY_COMMENT.getType()==nottificationDTO.getType()
        ||NotificationTypeEnum.REPLY_QUESTION.getType()==nottificationDTO.getType()
        ){
            return "redirect:/question/"+nottificationDTO.getOuterid();
        }else{
            return "/";
        }

    }
}
