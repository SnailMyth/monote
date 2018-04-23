//package com.myth.controller;
//
//import com.myth.base.ResponseMessage;
//import com.myth.base.SocketMessage;
//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.handler.annotation.SendTo;
//import org.springframework.stereotype.Controller;
//
//@Controller
//public class WebSocketController {
//    @MessageMapping("/welcome")
//    @SendTo("/topic/getResponse")
//    public ResponseMessage say(SocketMessage message) throws Exception {
//        //等待3秒返回消息内容
//        Thread.sleep(3000);
//        return new ResponseMessage("欢迎使用webScoket：" + message.getName());
//    }
//}
