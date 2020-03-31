package com.liu.community.controller;

import com.liu.community.dto.AccessTokenDTO;
import com.liu.community.model.User;
import com.liu.community.provider.GithubProvider;
import com.liu.community.dto.GithubUser;
import com.liu.community.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@Controller
@Slf4j
//登录操作
public class AuthorizeContriller {

    @Autowired
    private GithubProvider githubProvider;
    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.client.secret}")
    private String clientSecret;

    @Value("${github.redirect.uri}")
    private String redirectUri;

    @Autowired
    private UserService userService;

    @GetMapping("/callback")
    public String callback(@RequestParam(name="code")String code
    , @RequestParam(name = "state")String state, HttpServletRequest request, HttpServletResponse response) throws IOException {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        System.out.println(accessToken);
        GithubUser githubUser = githubProvider.getUser(accessToken);
        System.out.println(githubUser);

        if(githubUser!=null && githubUser.getId()!=null){
            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            if (githubUser.getName()==null){
                user.setName("匿名用户");
            }else{
                user.setName(githubUser.getName());
            }
            user.setAccountid(String.valueOf(githubUser.getId()));

            user.setAvatarurl(githubUser.getAvatar_url());
            userService.crearOrUpdate(user);
            //登录成功，写cooke和session
           response.addCookie(new Cookie("token",token));
           // request.getSession().setAttribute("user",githubUser);
            return "redirect:/";
        }else{
            log.error("githubUser有异常"+githubUser);
            //登录失败，重新登录
            return "redirect:/";
        }
    }
    @GetMapping("/logout")
    public String logout(HttpServletRequest request,HttpServletResponse response){
        request.getSession().removeAttribute("user");
        Cookie cookie=new Cookie("token",null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";
    }
}
