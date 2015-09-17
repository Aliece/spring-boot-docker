package org.aliece.docker.domain;

import lombok.Data;

import java.util.Date;

/**
 * Created by zhangsaizhong on 15/9/7.
 */
@Data
public class Product {
    private long ProductID;
    private String ProductName;
    private String ProductDesc;
}
