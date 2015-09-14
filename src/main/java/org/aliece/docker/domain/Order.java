package org.aliece.docker.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by zhangsaizhong on 15/9/7.
 */
@Data
public class Order implements Serializable {
    private long OrderID;
    private long AccountID;
    private long ProductID;
    private Date LastModifyTime;
    private Date CreateTime;
}
