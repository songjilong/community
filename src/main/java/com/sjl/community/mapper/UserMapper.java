package com.sjl.community.mapper;

import com.sjl.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @author song
 * @create 2020/2/16 13:14
 */
@Mapper
public interface UserMapper {

    @Insert("insert into user(account_id, name, token, gmt_create, gmt_modified, avatar_url) values(#{accountId}, #{name}, #{token}, #{gmtCreate}, #{gmtModified}, #{avatarUrl})")
    void insertUser(User user);

    @Select("select * from user where token = #{token}")
    User findByToken(String token);

    @Select("select * from user where id = #{id}")
    User findById(Long id);

    @Select("select * from user where account_id = #{accountId}")
    User findByAccountId(String accountId);

    @Update("update user set name=#{name}, bio=#{bio}, token=#{token}, avatar_url=#{avatarUrl}, gmt_modified=#{gmtModified} where account_id=#{accountId}")
    void updateUser(User dbUser);
}
