package com.myth.controller;


import com.myth.domain.user.Account;
import com.myth.service.AccountService;
import com.myth.test.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private AccountService service;



    @RequestMapping("/getInfo")
    @ResponseBody
    public User getInfo() {
        User user = new User("myth","111",11);

        return user;
    }


    @RequestMapping("/list")
    @ResponseBody
    public List<Account> getPageAccount() {
        List<Account> accounts =service.getPageAccount(1);
        return accounts;
    }

    @RequestMapping("/delete/{name}")
    @ResponseBody
    public String getPageAccount(@PathVariable("name") String name) {
        service.deleteAccount(name);
        return "success";
    }




}
