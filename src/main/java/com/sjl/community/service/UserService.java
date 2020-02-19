package com.sjl.community.service;

import com.sjl.community.mapper.UserMapper;
import com.sjl.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author song
 * @create 2020/2/18 22:19
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 根据user的accountId判断用户是否存在，存在则更新信息，不存在则创建新用户
     * @param user
     */
    public void createOrUpdateUser(User user) {
        //获取数据库里的user
        User dbUser = userMapper.findByAccountId(user.getAccountId());

        if(dbUser == null){
            //创建，创建时间修改时间均设置为当前时间
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insertUser(user);
        }else{
            //更新修改时间和头像
            dbUser.setGmtModified(System.currentTimeMillis());
            dbUser.setName(user.getName());
            dbUser.setBio(user.getBio());
            dbUser.setToken(user.getToken());
            dbUser.setAvatarUrl(user.getAvatarUrl());
            userMapper.updateUser(dbUser);
        }
    }
}
