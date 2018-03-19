package com.myth.service;

import com.myth.config.EmailConfig;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.List;
import java.util.Map;

@Service
public class EmailService {
    @Autowired
    private EmailConfig emailConfig;
    @Autowired
    private JavaMailSender mailSender;

    /**
     * 发送简单邮件
     *
     * @param sendTo  收件人地址
     * @param titel   邮件标题
     * @param content 邮件内容
     */
    public void sendSimpleMail(String sendTo, String titel, String content) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(emailConfig.getEmailFrom());
        mailMessage.setTo(sendTo);
        mailMessage.setSubject(titel);
        mailMessage.setText(content);
        mailSender.send(mailMessage);
    }

    /**
     * 发送带附件的简单邮件
     *
     * @param sendTo              收件人地址
     * @param titel               邮件标题
     * @param content             邮件内容
     * @param attachments<文件名，附件> 附件列表
     */
    public void sendAttachmentsMail(String sendTo, String titel, String content, List<Pair<String, File>> attachments) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message,true);
            helper.setFrom(emailConfig.getEmailFrom());
            helper.setTo(sendTo);
            helper.setSubject(titel);
            helper.setText(content);

            for (Pair<String,File> pair:attachments){
                helper.addAttachment(pair.getKey(),new FileSystemResource(pair.getValue()));
            }
        } catch (MessagingException e) {
            throw new RuntimeException();
        }

        mailSender.send(message);
    }

    /**
     * 发送模板邮件
     *
     * @param sendTo              收件人地址
     * @param titel               邮件标题
     * @param content<key,        内容> 邮件内容
     * @param attachments<文件名，附件> 附件列表
     */
    public void sendTemplateMail(String sendTo, String titel, Map<String, Object> content, List<Pair<String, File>> attachments) {

    }

}
