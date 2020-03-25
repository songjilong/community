package com.sjl.community.service;

import com.sjl.community.mapper.UserMapper;
import com.sjl.community.model.User;
import com.sjl.community.model.UserExample;
import com.sjl.community.utils.CodecUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

/**
 * @author song
 * @create 2020/3/24 13:02
 */
@Service
@Slf4j
public class RegisterService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private JavaMailSender mailSender;

    private static final String CODE_PRE = "code";

    @Value("${beetle.sender.email}")
    private String senderEmail;

    /**
     * 发送邮件
     *
     * @param email
     */
    public Boolean sendEmail(String email) {
        if(!registered(email)){
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setTo(email);
            simpleMailMessage.setSubject("测试");
            //生成6位随机数
            int code = (int) ((Math.random() * 9 + 1) * 100000);
            //存入redis，过期时间5分钟
            redisTemplate.opsForValue().set(CODE_PRE + email, code, 5, TimeUnit.MINUTES);
            simpleMailMessage.setText("这是一封测试邮件，验证码：" + code);
            simpleMailMessage.setFrom(senderEmail);
            try {
                mailSender.send(simpleMailMessage);
                return true;
            } catch (MailException e) {
                log.error("邮件发送出错，{}", e);
            }
        }
        return false;
    }

    /**
     * 判断验证码是否正确
     *
     * @param email
     * @param code
     * @return
     */
    public boolean checkCode(String email, Integer code) {
        Integer redisCode = (Integer) redisTemplate.opsForValue().get(CODE_PRE + email);
        return redisCode != null && redisCode.equals(code);
    }

    /**
     * 删除验证码
     *
     * @param email
     */
    public void deleteCode(String email) {
        redisTemplate.delete(CODE_PRE + email);
    }

    /**
     * 校验邮箱
     *
     * @param email
     * @return
     */
    public Boolean checkEmail(String email) {
        String eRegEx = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
        return Pattern.matches(eRegEx, email);
    }

    /**
     * 校验注册信息是否合法
     *
     * @param email
     * @param password
     * @param code
     * @return
     */
    public Boolean checkInfo(String email, String password, Integer code) {
        String eRegEx = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
        String pRegEx = "^[a-zA-Z0-9_-]{6,16}$";
        String cRegEx = "^[0-9]{6}$";
        return Pattern.matches(eRegEx, email)
                && Pattern.matches(pRegEx, password)
                && Pattern.matches(cRegEx, String.valueOf(code));
    }

    public Boolean registered(String email){
        UserExample example = new UserExample();
        example.createCriteria().andAccountIdEqualTo(email);
        List<User> users = userMapper.selectByExample(example);
        return users.size() > 0;
    }

    /**
     * 邮箱注册
     *
     * @param email
     * @param password
     * @return
     */
    public Boolean register(String email, String password) {
        //注册
        if (!registered(email)) {
            User record = new User();
            record.setAccountId(email);
            record.setToken(UUID.randomUUID().toString());
            record.setName("邮箱用户_" + email);
            record.setGmtCreate(System.currentTimeMillis());
            record.setGmtModified(record.getGmtCreate());
            record.setAvatarUrl("https://gitee.com/songjilong/FigureBed/raw/master/img/20200324171059.png");
            //生成盐
            String salt = CodecUtils.generateSalt();
            record.setSalt(salt);
            //对密码加密
            record.setPassword(CodecUtils.md5Hex(password, salt));
            boolean b = this.userMapper.insertSelective(record) == 1;
            if (b) {
                //删除验证码
                deleteCode(email);
                return true;
            }
        }
        return false;
    }
}
