package org.aliece.docker.repository.mapper;

import org.aliece.docker.domain.Product;
import org.aliece.docker.persistence.MybatisMapper;

/**
 * Created by zhangsaizhong on 15/9/7.
 */
@MybatisMapper
public interface ProductMapper {
    Product selectByProductID(String productId);

    void insert(Product product);

}
