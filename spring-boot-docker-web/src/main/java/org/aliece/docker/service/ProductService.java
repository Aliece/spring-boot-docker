package org.aliece.docker.service;

import org.aliece.docker.domain.Product;
import org.aliece.docker.repository.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhangsaizhong on 15/9/9.
 */
@Service
public class ProductService {

    @Autowired
    private ProductMapper productMapper;

    public void insert(Product product) {
        productMapper.insert(product);
    }

    public List<Product> findAll(){ return productMapper.findAll(new PageRequest(0,10));}
}
