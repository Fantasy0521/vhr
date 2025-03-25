package org.javaboy.vhr.controller;

import org.javaboy.vhr.config.TongYiUtil;
import org.javaboy.vhr.model.ChatMsg;
import org.javaboy.vhr.model.Hr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.Date;

@Controller
public class WsController {
    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;


    @Autowired
    private TongYiUtil tongYiUtil;


    @MessageMapping("/ws/chat")
    public void handleMsg(Authentication authentication, ChatMsg chatMsg) {
        Hr hr = (Hr) authentication.getPrincipal();
        chatMsg.setFrom(hr.getUsername());
        chatMsg.setFromNickname(hr.getName());
        chatMsg.setDate(new Date());
        simpMessagingTemplate.convertAndSendToUser(chatMsg.getTo(), "/queue/chat", chatMsg);
        if ("ai".equals(chatMsg.getTo())){
            // 调用AI接口
            String result = tongYiUtil.questAI(chatMsg.getContent());
            // 发送ai的回复给用户
            chatMsg.setFrom("ai");
            chatMsg.setFromNickname("HR小助手(AI)");
            chatMsg.setTo(hr.getUsername());
            chatMsg.setContent(result);
            simpMessagingTemplate.convertAndSendToUser(chatMsg.getTo(), "/queue/chat", chatMsg);
        }
    }
}
