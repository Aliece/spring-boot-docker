package org.aliece.docker.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * Created by zhangsaizhong on 15/9/7.
 */
@Configuration
@ImportResource(value = "classpath:beans.xml")
public class PersistenceConfiguration {

    @Autowired
    MybatisProperties mybatisProperties;

    @Autowired
    DruidProperties druidProperties;

    @Bean//(name = "dbDataSource", initMethod = "init", destroyMethod = "close")
    public DataSource dataSource() throws SQLException {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(druidProperties.getUrl());
        dataSource.setUsername(druidProperties.getUsername());
        dataSource.setPassword(druidProperties.getPassword());
        dataSource.setInitialSize(druidProperties.getInitialSize());
        dataSource.setMinIdle(druidProperties.getMinIdle());
        dataSource.setMaxActive(druidProperties.getMaxActive());
        dataSource.setMaxWait(druidProperties.getMaxWait());
        dataSource.setTimeBetweenEvictionRunsMillis(druidProperties.getTimeBetweenEvictionRunsMillis());
        dataSource.setMinEvictableIdleTimeMillis(druidProperties.getMinEvictableIdleTimeMillis());
        dataSource.setValidationQuery(druidProperties.getValidationQuery());
        dataSource.setTestWhileIdle(druidProperties.getTestWhileIdle());
        dataSource.setTestOnBorrow(druidProperties.getTestOnBorrow());
        dataSource.setTestOnReturn(druidProperties.getTestOnReturn());
        dataSource.setPoolPreparedStatements(druidProperties.getPoolPreparedStatements());
        dataSource.setMaxPoolPreparedStatementPerConnectionSize(druidProperties.getMaxPoolPreparedStatementPerConnectionSize());
        dataSource.setFilters(druidProperties.getFilters());
        return dataSource;
    }

    @Bean
    public PlatformTransactionManager txManager() throws Exception{
        return new DataSourceTransactionManager(dataSource());
    }


    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource());
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sqlSessionFactoryBean.setMapperLocations(resolver.getResources(mybatisProperties.getMapperLocations()));
        sqlSessionFactoryBean.setTypeAliasesPackage(mybatisProperties.getTypeAliasedPackage());

        return sqlSessionFactoryBean.getObject();
    }

    /*
     * 以下的这个方法也是可以进行声明Bean的，不过如果想使用@Value来注入properties或者yml中声明的值
     * 是不可以的，因为这是一个工厂类，Spring会优先加载这里，这就会造成@Value注入的值是null
     * 当然也可以使用这样的方式，但是如果想改变配置，则需要重新改这里的代码编译再部署，不够灵活。
     * 退而求其次，MyBatis还是使用xml的形式进行配置
     */
   /* @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() throws ClassNotFoundException {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setBasePackage(mybatisProperties.getBasePackage());
        Class<? extends Annotation> clazz = (Class<? extends Annotation>) Class.forName(mybatisProperties.getAnnotationClass());
        mapperScannerConfigurer.setAnnotationClass(clazz);

        return mapperScannerConfigurer;
    }*/

}
