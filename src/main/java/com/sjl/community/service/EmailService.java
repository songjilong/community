package com.sjl.community.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author song
 * @create 2020/3/24 10:33
 */
@Service
@Slf4j
public class EmailService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private JavaMailSender mailSender;

    private static final String CODE_PRE = "code";

    @Value("${beetle.sender.email}")
    private String senderEmail;

    /**
     * 发送邮件
     * @param email
     */
    public void sendEmail(String email) {
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
        } catch (MailException e) {
            log.error("发送邮件出错", e);
        }
    }

    /**
     * 判断验证码是否正确
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
     * @param email
     */
    public void deleteCode(String email){
        redisTemplate.delete(CODE_PRE + email);
    }
}
