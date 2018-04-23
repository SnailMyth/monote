package com.myth.controller;

import com.myth.base.*;
import com.myth.domain.user.Account;
import com.myth.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    private AccountService service;

    @RequestMapping(value = {"/index", "/"})
    public ModelAndView index(HttpSession session) {
        Account user = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("user",user);
        return modelAndView;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Account account, HttpServletRequest request, HttpServletResponse response) {
        return "login";
    }

    @RequestMapping(value = "/registerView")
    public ModelAndView registerView(){
        ModelAndView modelAndView = new ModelAndView("registerView");
        return modelAndView;
    };

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    @ResponseBody
    public ApiModel register(Account account, HttpServletRequest request, HttpServletResponse response) {
        ApiModel model = new ApiModel();
        if (account == null){
            model.setError(BaseException.create(Errors.REGISTER_ACCOUNT_FAIL));
        }
        if (StringUtils.isEmpty(account.getUsername())
            ||StringUtils.isEmpty(account.getPassword())){
            model.setError(BaseException.create(Errors.REGISTER_NAME_OR_PASSWORD_VOID));
        }

        Account account1 = null;
        try{
            account1 = service.addAccount(account);
        }catch (Exception e){
            model.setError(BaseException.create(Errors.REGISTER_CREATE_ACCOUNT_FAIL));
        }
        model.setData(account1);
        return model;
    }

}
