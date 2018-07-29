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
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
@MapperScan(basePackages = "com.cd.xaboot.mapper.user", sqlSessionTemplateRef = "userSqlSessionTemplate")
public class UserDbConfig {

    @Primary
    @Bean(name = "userDataSource")
    public DataSource oneDataSource(UserDbProperties userDbProperties) throws SQLException {
        MysqlXADataSource mysqlXADataSource = new MysqlXADataSource();
        mysqlXADataSource.setUrl(userDbProperties.getUrl());
        mysqlXADataSource.setUser(userDbProperties.getUsername());
        mysqlXADataSource.setPassword(userDbProperties.getPassword());
        AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
        xaDataSource.setXaDataSource(mysqlXADataSource);
        xaDataSource.setUniqueResourceName("userDataSource");
        xaDataSource.setMinPoolSize(userDbProperties.getMinPoolSize());
        xaDataSource.setMaxPoolSize(userDbProperties.getMaxPoolSize());
        xaDataSource.setMaxLifetime(userDbProperties.getMaxLifetime());
        xaDataSource.setBorrowConnectionTimeout(userDbProperties.getBorrowConnectionTimeout());
        xaDataSource.setLoginTimeout(userDbProperties.getLoginTimeout());
        xaDataSource.setMaintenanceInterval(userDbProperties.getMaintenanceInterval());
        xaDataSource.setMaxIdleTime(userDbProperties.getMaxIdleTime());
        xaDataSource.setTestQuery(userDbProperties.getTestQuery());
        return xaDataSource;
    }
    @Bean(name = "userSqlSessionFactory")
    public SqlSessionFactory oneSqlSessionFactory(@Qualifier("userDataSource") DataSource dataSource)
            throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(
                new PathMatchingResourcePatternResolver().getResources("classpath:/mapper/user/*.xml"));
        return bean.getObject();
    }
    @Bean(name = "userSqlSessionTemplate")
    public SqlSessionTemplate oneSqlSessionTemplate(
            @Qualifier("userSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
