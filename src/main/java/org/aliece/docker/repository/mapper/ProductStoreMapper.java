package org.aliece.docker.repository.mapper;

import org.aliece.docker.domain.ProductStore;
import org.aliece.docker.persistence.MybatisMapper;

/**
 * Created by zhangsaizhong on 15/9/7.
 */
@MybatisMapper
public interface ProductStoreMapper {
    ProductStore selectByProductID(String productId);

    void update(ProductStore productStore);

    void insert(ProductStore productStore);

}
