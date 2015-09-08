package org.aliece.docker.repository.mapper;

import org.aliece.docker.domain.User;
import org.aliece.docker.persistence.MybatisMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by zhangsaizhong on 15/9/7.
 */
@MybatisMapper
public interface UserMapper {
    User selectUser(String userName);

    User selectByID(String userId);

    void insert(User user);

    Page<User> findAll(Pageable var1);
}
