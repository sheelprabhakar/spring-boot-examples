package org.c4c.mysql.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DynamicDataSourceConfiguration {

    @Value("${spring.datasource.default.url}")
    private String defaultDbUrl;

    @Value("${spring.datasource.default.username}")
    private String defaultDbUsername;

    @Value("${spring.datasource.default.password}")
    private String defaultDbPassword;

    @Value("${spring.datasource.default.driver-class-name}")
    private String defaultDbDriverClassName;

    @Bean
    public DataSource dataSource() {
        // Create a single default DataSource
        HikariDataSource defaultDataSource = new HikariDataSource();
        defaultDataSource.setJdbcUrl(defaultDbUrl);
        defaultDataSource.setUsername(defaultDbUsername);
        defaultDataSource.setPassword(defaultDbPassword);
        defaultDataSource.setDriverClassName(defaultDbDriverClassName);

        return defaultDataSource;
    }
}
