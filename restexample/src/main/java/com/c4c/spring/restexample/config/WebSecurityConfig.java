package com.c4c.spring.restexample.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        // Below config will allow only following origines from web browser
        corsConfiguration.setAllowedOrigins(Arrays.asList("code4copy.com", "https://abc.org",
                "http://xyz.com", "http://localhost:8080"));
        // Whether user credentials are supported. By default, do not support
        // If you want to allow credentials then set it true
        corsConfiguration.setAllowCredentials(false);

        // below will not allow DELETE methods, if you want then add DELETE also
        corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "OPTIONS"));

        // Below will set allowed headers list, Other headers will not be supported
        corsConfiguration.setAllowedHeaders(Arrays.asList("accept", "authorization", "apikey", "tenantId"));

        UrlBasedCorsConfigurationSource  corsConfigurationSource = new UrlBasedCorsConfigurationSource();

        // This will register above configurations on all resources from the root
        // If you want different rules for different resources then create separate configuration
        // and register on separate resource path uri
        corsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
        return corsConfigurationSource;
    }

    @Bean
    public FilterRegistrationBean corsFilterRegistrationBean(CorsConfigurationSource corsConfigurationSource){
        CorsFilter corsFilter = new CorsFilter(corsConfigurationSource);
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        filterRegistrationBean.setFilter(corsFilter);
        return filterRegistrationBean;
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // Here configure all security stuff
        http.authorizeRequests().anyRequest().permitAll()
                .and().cors(); // .crs().disable() to disable cors
        http.headers().frameOptions().sameOrigin();

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        // Ignore resources for any check
        return (web) -> web.ignoring().antMatchers("/images/**", "/js/**", "/webjars/**");
    }
}
