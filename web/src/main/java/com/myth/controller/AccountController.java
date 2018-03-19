package com.myth.controller;

import com.myth.LoggerUtils;
import com.myth.domain.Account;
import com.myth.domain.User;
import com.myth.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService service;

    @RequestMapping("/getInfo")
    @ResponseBody
    public Account getInfo() {
        return service.getInfo("myth");
    }


    @RequestMapping("/list")
    @ResponseBody
    public List<Account> getPageAccount() {
        return service.getPageAccount(1);
    }

    @RequestMapping("/list2")
    @ResponseBody
    public List<Account> getPageAccount2() {
        return service.getPageAccount(2);
    }

    @RequestMapping("/add")
    @ResponseBody
    public Account accountAdd() {
        Account account = new Account();
        account.setUsername("myth");
        account.setPassword("123");
        account.setActive(true);
        return service.aadAccount(account,null);
    }

    @RequestMapping("/add1")
    @ResponseBody
    public Account accountAdd1() {
        Account account = new Account();
        account.setUsername("myth1");
        account.setPassword("123");
        account.setActive(true);
        User user = new User();
        user.setNickName("myth_hai");
        user.setEmail("89938298@qq.com");
        user.setSex(true);
        return service.aadAccount(account,user);
    }

    @RequestMapping("/add2")
    @ResponseBody
    public Account accountAdd2() {
        Account account = new Account();
        account.setUsername("myth2");
        account.setPassword("123");
        account.setActive(true);
        User user = new User();
        user.setNickName("myth_hai");
        user.setEmail("89938298");
        user.setSex(true);
        return service.aadAccount(account,user);
    }

    @RequestMapping("/check")
    public void check(Account account, HttpServletRequest request,HttpServletResponse response) {
        Account info = service.getInfo(account.getUsername());
        try {
        if (null != info) {
            request.getSession().setAttribute("session_account",info);
            request.setAttribute(LoggerUtils.LOGGER_RETURN,info);
            response.sendRedirect("/index");
        } else {
            request.setAttribute(LoggerUtils.LOGGER_RETURN,"login fail");
            response.sendRedirect("/login");
        }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
