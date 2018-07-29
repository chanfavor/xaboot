package com.cd.xaboot.config;

import com.atomikos.jdbc.AtomikosDataSourceBean;
import com.mysql.cj.jdbc.MysqlXADataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
@MapperScan(basePackages = "com.cd.xaboot.mapper.order", sqlSessionTemplateRef = "orderSqlSessionTemplate")
public class OrderDbConfig {
    @Bean(name = "orderDataSource")
    public DataSource orderDataSource(OrderDbProperties orderDbProperties) throws SQLException {
        MysqlXADataSource mysqlXaDataSource = new MysqlXADataSource();
        mysqlXaDataSource.setUrl(orderDbProperties.getUrl());
        mysqlXaDataSource.setPassword(orderDbProperties.getPassword());
        mysqlXaDataSource.setUser(orderDbProperties.getUsername());
//        mysqlXaDataSource.setPinGlobalTxToPhysicalConnection(true);
        AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
        xaDataSource.setXaDataSource(mysqlXaDataSource);
        xaDataSource.setUniqueResourceName("orderDataSource");
        xaDataSource.setMinPoolSize(orderDbProperties.getMinPoolSize());
        xaDataSource.setMaxPoolSize(orderDbProperties.getMaxPoolSize());
        xaDataSource.setMaxLifetime(orderDbProperties.getMaxLifetime());
        xaDataSource.setBorrowConnectionTimeout(orderDbProperties.getBorrowConnectionTimeout());
        xaDataSource.setLoginTimeout(orderDbProperties.getLoginTimeout());
        xaDataSource.setMaintenanceInterval(orderDbProperties.getMaintenanceInterval());
        xaDataSource.setMaxIdleTime(orderDbProperties.getMaxIdleTime());
        xaDataSource.setTestQuery(orderDbProperties.getTestQuery());
        return xaDataSource;
    }
    @Bean(name = "orderSqlSessionFactory")
    public SqlSessionFactory orderSqlSessionFactory(@Qualifier("orderDataSource") DataSource dataSource)
            throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(
                new PathMatchingResourcePatternResolver().getResources("classpath:/mapper/order/*.xml"));
        return bean.getObject();
    }
    @Bean(name = "orderSqlSessionTemplate")
    public SqlSessionTemplate orderSqlSessionTemplate(
            @Qualifier("orderSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}