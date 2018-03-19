package com.myth.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@PropertySource(value = "application-mail.properties")
@Configuration
@Component
public class EmailConfig {
    @Value("${spring.mail.username}")
    private   String emailFrom ;

    public String getEmailFrom() {
        return emailFrom;
    }

    public void setEmailFrom(String emailFrom){
        this.emailFrom = emailFrom;
    }
}
