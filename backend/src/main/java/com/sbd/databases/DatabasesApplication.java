package com.sbd.databases;

import com.sbd.databases.filter.JwtTokenUtil;
import com.sbd.databases.filter.ManagerAuthenticationFilter;
import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import java.util.Collections;

@SpringBootApplication
@EnableEncryptableProperties
public class DatabasesApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(DatabasesApplication.class, args);
    }

    @Autowired
    @Bean
    public FilterRegistrationBean<ManagerAuthenticationFilter> filterRegistrationBean(JwtTokenUtil jwtTokenUtil)
    {
        FilterRegistrationBean<ManagerAuthenticationFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new ManagerAuthenticationFilter(jwtTokenUtil));
        filterRegistrationBean.setUrlPatterns(Collections.singletonList("/manager/secure/*"));

        return filterRegistrationBean;
    }

}
