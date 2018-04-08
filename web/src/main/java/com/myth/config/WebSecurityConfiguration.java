package com.myth.config;

import com.myth.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {



    @Bean
    public UserDetailsService userDetailsService() {
        return new AccountService();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/css/**","/img/**","/js/**").permitAll()
                .antMatchers("/index").hasRole("USER")
                .antMatchers("/admin").hasRole("ADMIN")
                .antMatchers("/account/**").hasRole("USER")
                .anyRequest().authenticated()//表示其他的请求都必须要有权限认证。
                .and()
                    .formLogin()
                    .loginPage("/login")
                    .failureForwardUrl("/login-error")
                    .successForwardUrl("/index")
                    .permitAll()
                .and()
                    .logout()
                    .permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService());
    }

    @Bean
    public static NoOpPasswordEncoder passwordEncoder() {
        return (NoOpPasswordEncoder)NoOpPasswordEncoder.getInstance();
    }
}
