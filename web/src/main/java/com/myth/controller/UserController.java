package com.myth.controller;


import com.myth.annotation.ContentSecurityAttribute;
import com.myth.annotation.ParameterModel;
import com.myth.domain.user.Account;
import com.myth.service.AccountService;
import com.myth.test.Student;
import com.myth.test.Teacher;
import com.myth.test.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private AccountService service;

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

    @RequestMapping("/info")
    @ResponseBody
    public String getInfo(@ParameterModel Student student, @ParameterModel Teacher teacher) {
        String str = "fail";
        if(student.getAge() != 0 && teacher.getName() !=null){
            str = "success";
        }

        return str;
    }

    @RequestMapping("/info1")
    @ResponseBody
    public String reAimParameters(@ContentSecurityAttribute("user")UserEntity user) {
        String str = "fail";
        if(user.getAge() != 0 && user.getName() !=null){
            str = "success";
        }
        return str;
    }




}
