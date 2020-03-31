package com.liu.community.controller;

import com.liu.community.cache.TagCache;
import com.liu.community.dto.QuestionDTO;
import com.liu.community.model.Question;
import com.liu.community.model.User;
import com.liu.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
//发布和编辑
@Controller
public class PublishCpmtorller {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/publish/{id}")
    public String publish(@PathVariable(name = "id")Long id, Model model){
        QuestionDTO byid = questionService.getByid(id);
        model.addAttribute("title",byid.getTitle());
        model.addAttribute("description",byid.getDescription());
        model.addAttribute("tag",byid.getTag());
        model.addAttribute("id",byid.getId());
        model.addAttribute("tags", TagCache.get());
        return "publish";
    }


    @GetMapping("/publish")
    public String publish(Model model){
        model.addAttribute("tags", TagCache.get());
    return "publish";
    }


    //发布表单按钮
    @PostMapping("/publish")
    public String doPublish(@RequestParam("title") String title, @RequestParam(value = "description",required = false) String description, @RequestParam("tag") String tag
    , HttpServletRequest request, Model model
    ,@RequestParam("id") Long id){

        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);
        model.addAttribute("tags", TagCache.get());

        if(title==null||title==""){
            model.addAttribute("error","标题不能为空");
            return "publish";
        }

        if(description==null||description==""){
            model.addAttribute("error","问题不能为空");
            return "publish";
        }

        if(tag==null||tag==""){
            model.addAttribute("error","标签不能为空");
            return "publish";
        }

        //获取拦截器的值
        User user = (User)request.getSession().getAttribute("user");
        if(user==null){
            model.addAttribute("error","用户未登陆");
            return "publish";
        }

        //接受发布的内容
        Question question = new Question();
        question.setId(id);
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        questionService.createOrUpdate(question);

        return "redirect:/";

    }


}
