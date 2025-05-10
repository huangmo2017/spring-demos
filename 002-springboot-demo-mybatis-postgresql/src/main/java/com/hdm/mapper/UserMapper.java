package com.hdm.mapper;

import com.hdm.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {

    @Select("SELECT * FROM users")
    List<User> findAll();

    @Select("SELECT * FROM users WHERE id = #{id}")
    User findById(Integer id);

    @Insert("INSERT INTO users (username, password, email) VALUES (#{username}, #{password}, #{email})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(User user);

    @Update("UPDATE users SET username=#{username}, password=#{password}, email=#{email} WHERE id=#{id}")
    int update(User user);

    @Delete("DELETE FROM users WHERE id=#{id}")
    int deleteById(Integer id);
}
