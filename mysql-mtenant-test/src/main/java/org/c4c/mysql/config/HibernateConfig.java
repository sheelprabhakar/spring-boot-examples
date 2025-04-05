package org.c4c.mysql.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
public class HibernateConfig {

    @Autowired
    private DataSource dataSource;

    @Bean
    public Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.multi_tenant_connection_provider", new SchemaBasedMultiTenantConnectionProvider(dataSource));
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
        return properties;
    }
}