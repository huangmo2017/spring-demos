package com.hdm.service;

import com.hdm.entity.User;
import com.hdm.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public List<User> getAllUsers() {
        return userMapper.findAll();
    }

    public User getUserById(Integer id) {
        return userMapper.findById(id);
    }

    public int addUser(User user) {
        return userMapper.insert(user);
    }

    public int updateUser(User user) {
        return userMapper.update(user);
    }

    public int deleteUser(Integer id) {
        return userMapper.deleteById(id);
    }
}
