package org.aliece.docker.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by zhangsaizhong on 15/9/7.
 */
@Data
@Component
@ConfigurationProperties(prefix = "spring.mybatis")
public class MybatisProperties {

    private String typeAliasedPackage;

    private String mapperLocations;

    private String annotationClass;

    private String basePackage;

}
