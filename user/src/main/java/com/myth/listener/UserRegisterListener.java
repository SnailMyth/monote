package com.myth.listener;

import com.myth.domain.user.Account;
import com.myth.event.UserRegisterEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class UserRegisterListener  {

    @EventListener
    public void register(UserRegisterEvent event){
        Account account = event.getAccount();
        System.out.println(account);
    }
}
