package com.sjl.community.mapper;

import com.sjl.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author song
 * @create 2020/2/16 13:14
 */
@Mapper
public interface UserMapper {

    @Insert("insert into user(account_id, name, token, gmt_create, gmt_modified) values(#{accountId}, #{name}, #{token}, #{gmtCreate}, #{gmtModified})")
    void insertUser(User user);

    @Select("select * from user where token = #{token}")
    User findByToken(String token);
}
