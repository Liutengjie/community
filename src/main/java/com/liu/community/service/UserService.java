package com.liu.community.service;

import com.liu.community.mapper.UserMapper;
import com.liu.community.model.User;
import com.liu.community.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserMapper usermapper;

    public void crearOrUpdate(User user) {
        UserExample userExample=new UserExample();
        userExample.createCriteria().andAccountidEqualTo(user.getAccountid());
        List<User> users = usermapper.selectByExample(userExample);//通过Accountid查询
        if (users.size()==0){
            user.setGmtcreate(System.currentTimeMillis());//创建时间
            user.setGmtmodified(user.getGmtcreate());//最新登录时间
            usermapper.insert(user);
        }else{
            User dbuser=users.get(0);
            User updataUser=new User();
            updataUser.setGmtmodified(System.currentTimeMillis());//最新登录时间
            updataUser.setAvatarurl(user.getAvatarurl());
            updataUser.setName(user.getName());
            updataUser.setToken(user.getToken());
            UserExample example=new UserExample();
            example.createCriteria().andIdEqualTo(dbuser.getId());
            usermapper.updateByExampleSelective(updataUser,example);//更新
        }
    }
}
