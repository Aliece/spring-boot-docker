package org.aliece.docker.repository.mapper;

import org.aliece.docker.domain.Order;
import org.aliece.docker.domain.User;
import org.aliece.docker.persistence.MybatisMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by zhangsaizhong on 15/9/7.
 */
@MybatisMapper
public interface OrderMapper {
    Order selectByProductID(String productId);

    Order selectByUserID(String accountId);

    void insert(Order order);

}
