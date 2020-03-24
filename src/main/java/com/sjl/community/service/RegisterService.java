package com.sjl.community.service;

import com.sjl.community.dto.ResultDto;
import com.sjl.community.exception.CustomizeErrorCode;
import com.sjl.community.mapper.UserMapper;
import com.sjl.community.model.User;
import com.sjl.community.model.UserExample;
import com.sjl.community.utils.CodecUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * @author song
 * @create 2020/3/24 13:02
 */
@Service
public class RegisterService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private EmailService emailService;

    /**
     * 邮箱注册
     * @param email
     * @param password
     * @return
     */
    public ResultDto register(String email, String password) {
        UserExample example = new UserExample();
        example.createCriteria().andAccountIdEqualTo(email);
        List<User> users = userMapper.selectByExample(example);
        //注册
        if(users.size() == 0){
            User record = new User();
            record.setAccountId(email);
            record.setToken(UUID.randomUUID().toString());
            record.setName("邮箱用户_" + email);
            record.setGmtCreate(System.currentTimeMillis());
            record.setGmtModified(record.getGmtCreate());
            record.setAvatarUrl("http://thirdqq.qlogo.cn/g?b=oidb&k=ZRetjMVzPxMicx27ianRDCSg&s=100&t=1564116601");
            //生成盐
            String salt = CodecUtils.generateSalt();
            record.setSalt(salt);
            //对密码加密
            record.setPassword(CodecUtils.md5Hex(password, salt));
            boolean b = this.userMapper.insertSelective(record) == 1;
            if(b){
                //删除验证码
                emailService.deleteCode(email);
            }
            return ResultDto.okOf();
        }
        //邮箱已注册
        else{
            return ResultDto.errorOf(CustomizeErrorCode.EMAIL_ALREADY_EXISTS);
        }
    }
}
