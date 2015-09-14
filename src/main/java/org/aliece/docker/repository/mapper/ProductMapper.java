package org.aliece.docker.repository.mapper;

import org.aliece.docker.domain.Product;
import org.aliece.docker.persistence.MybatisMapper;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by zhangsaizhong on 15/9/7.
 */
@MybatisMapper
public interface ProductMapper {
    Product selectByProductID(String productId);
    List<Product> findAll(Pageable var1);
    void insert(Product product);

}
