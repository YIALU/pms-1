package com.mioto.pms.config;

import com.zaxxer.hikari.HikariDataSource;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * 数据库连接池配置类
 * @author mioto-qinxj
 * @date 2020/9/25
 */
@Component
@ConfigurationProperties("mioto.project.datasource")
@Setter
public class HikariDataSourceConfig {
    private String url;
    private String username;
    private String password;

    @Bean
    public HikariDataSource dataSource(){
        HikariDataSource hikariDataSource = DataSourceBuilder.create().type(HikariDataSource.class).build();
        //连接池的用户定义名称
        hikariDataSource.setPoolName("mioto hikari pool");
        //结合test-query验证连接的有效性
        hikariDataSource.setValidationTimeout(3000);
        hikariDataSource.setConnectionTestQuery("select 1");
        //连接超时时间:毫秒，小于250毫秒，否则被重置为默认值30秒
        hikariDataSource.setConnectionTimeout(60000);
        //连接最大存活时间.不等于0且小于30秒，会被重置为默认值30分钟.设置应该比mysql设置的超时时间短
        hikariDataSource.setMaxLifetime(60000);
        //此属性控制从池返回的连接的默认自动提交行为,默认值：true
        hikariDataSource.setAutoCommit(true);
        //连接池最大连接数，默认是10
        hikariDataSource.setMaximumPoolSize(10);
        //空闲连接超时时间，默认值600000（10分钟），大于等于max-lifetime且max-lifetime>0，会被重置为0；不等于0且小于10秒，会被重置为10秒
        //只有空闲连接数大于最大连接数且空闲时间超过该值，才会被释放
        hikariDataSource.setIdleTimeout(50000);
        //最小空闲连接数量
        hikariDataSource.setMinimumIdle(5);
        hikariDataSource.setDriverClassName("com.mysql.jdbc.Driver");
        hikariDataSource.setJdbcUrl(url);
        hikariDataSource.setUsername(username);
        hikariDataSource.setPassword(password);
        return hikariDataSource;
    }
}
