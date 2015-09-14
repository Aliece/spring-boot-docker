package org.aliece.docker.service;

import org.aliece.docker.domain.User;
import org.aliece.docker.repository.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhangsaizhong on 15/9/7.
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public User readUserByUsername(String username){
        User selectUser = userMapper.selectUser(username);
        return selectUser;
    }

    public AsyncResult findOne(String userID){
        return new AsyncResult<User>(userMapper.selectByID(userID));
    }

    public void insert(User user){
       userMapper.insert(user);
    }

    public List findUserByPage(Integer page, Integer rows){
        PageRequest pageRequest = new PageRequest(page, rows);
        return userMapper.findAll(pageRequest);
    }
}
