package com.myth.service;

import com.myth.dao.user.AccountDao;
import com.myth.dao.user.RolesDao;
import com.myth.domain.user.Account;
import com.myth.domain.user.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class InitService {

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private RolesDao rolesDao;


    @PostConstruct
    public void init(){
        Account preAdmin  = accountDao.getAccountInfo("myth");
        if (preAdmin == null){
            Roles admin = new Roles("ADMIN");
            Roles user = new Roles("USER");
            Account account = new Account();
            account.setActive(true);
            account.setUsername("myth");
            account.setPassword("111");
            account.getRoles().add(admin);
            account.getRoles().add(user);
            admin.setAccount(account);
            user.setAccount(account);
            accountDao.save(account);
        }
    }
}
