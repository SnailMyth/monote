package com.myth.controller;

import com.myth.base.ApiModel;
import com.myth.base.BaseException;
import com.myth.base.Errors;
import com.myth.service.EmailService;
import com.myth.util.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

@Controller
public class TestConotroller {

    @Autowired
    private EmailService service;

    @RequestMapping("/test/mail")
    public void sendMail(HttpServletResponse response) {
        service.sendSimpleMail("myth_hai@163.com", "发给小朋友", "这是发给小朋友的发送邮件");
        System.out.println("send finish");
        try {
            response.sendRedirect("/account/index");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @RequestMapping(value = {"/test/upload"})
    @ResponseBody
    public ApiModel imgupload(HttpServletRequest request, HttpServletResponse response, @RequestParam("file_data") MultipartFile file) {
        ApiModel model = new ApiModel();
        if (file.isEmpty()) {
            model.setError(BaseException.create(Errors.FILE_NULL));
            return  model;
        }

        String fileName = file.getOriginalFilename();
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        String filePath = FileUtils.getProjectPath();
        File dest = new File(filePath + fileName);
        // 检测是否存在目录
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }

        try {
            file.transferTo(dest);
        } catch (IOException e) {
            e.printStackTrace();
            model.setError(BaseException.create(Errors.FILE_NULL));
        }
        return  model;
    }

    @RequestMapping("/test/websocket")
    public String webSocket(){
        return "webSocket";
    }

    @RequestMapping("/error/{number}")
    public String error(@PathVariable int number){
        System.out.println(20 / number);
        return "test";
    }

    @RequestMapping("/cors")
    @ResponseBody
    public String corsIndex(){
        return "this is cors info";
    }

}
