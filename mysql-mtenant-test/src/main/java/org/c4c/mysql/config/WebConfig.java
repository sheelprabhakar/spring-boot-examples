package org.c4c.mysql.config;

import org.c4c.mysql.filter.TenantFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfig {

    @Bean
    public FilterRegistrationBean<TenantFilter> tenantFilterRegistration() {
        FilterRegistrationBean<TenantFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new TenantFilter());
        registrationBean.addUrlPatterns("/*"); // Apply to all endpoints
        registrationBean.setOrder(1); // Set the order of the filter
        return registrationBean;
    }
}