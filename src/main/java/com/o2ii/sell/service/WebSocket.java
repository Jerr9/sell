package com.o2ii.sell.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
@ServerEndpoint(value = "/webSocket")
@Slf4j
public class WebSocket {
    private Session session;

    private static CopyOnWriteArraySet<WebSocket> webSocketSet = new CopyOnWriteArraySet<>();

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        webSocketSet.add(this);
        log.info("【WebSocket消息】新的连接， 总数 = {}", webSocketSet.size());
    }

    @OnClose
    public void onClose() {
        webSocketSet.remove(this);
        log.info("【WebSocket消息】断开连接， 总数 = {}", webSocketSet.size());
    }

    @OnMessage
    public void onMessage(String message) {
        log.info("【WebSocket消息】收到客户端发来的消息 = {}", message);
    }

    public void sendMessage(String message) {
        for(WebSocket webSocket : webSocketSet) {
            log.info("【WebSocket消息】广播消息 = {}", message);
            try {
                webSocket.session.getBasicRemote().sendText(message);
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
}
