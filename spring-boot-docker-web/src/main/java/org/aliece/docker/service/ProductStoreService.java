package org.aliece.docker.service;

import org.aliece.docker.domain.ProductStore;
import org.aliece.docker.repository.mapper.ProductStoreMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zhangsaizhong on 15/9/9.
 */
@Service
public class ProductStoreService {

    @Autowired
    private ProductStoreMapper productOrderMapper;

    public void insert(ProductStore productStore) {
        productOrderMapper.insert(productStore);
    }
}
