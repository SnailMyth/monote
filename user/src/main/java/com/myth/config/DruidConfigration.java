package com.myth.config;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DruidConfigration {


    @Bean
    public ServletRegistrationBean statViewServlet(){
        ServletRegistrationBean bean = new ServletRegistrationBean(new StatViewServlet(),"/druid/*");
        bean.addInitParameter("allow","127.0.0.1");
        bean.addInitParameter("loginUsername","druid");
        bean.addInitParameter("loginPassword","123456");
        bean.addInitParameter("resetEnable","false");
        return bean;
    }

    @Bean
    public FilterRegistrationBean setFilter(){
        FilterRegistrationBean bean = new FilterRegistrationBean(new WebStatFilter());
        bean.addUrlPatterns("/");
        bean.addInitParameter("exclusion","*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        return  bean;
    }
}
