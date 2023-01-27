package com.example.statistics.sockets;

import com.example.statistics.service.SessionStorage;
import com.example.statistics.sockets.spring.CustomSpringConfigurator;
import com.example.statistics.sockets.spring.SpringContext;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ServerEndpoint(value = "/stats/team", configurator = CustomSpringConfigurator.class)
public class TeamStatsWebSocket {
    private final SessionStorage sessionStorage = SpringContext.getBean(SessionStorage.class);

    @OnOpen
    public void onOpen(Session session) {
        sessionStorage.addSession(session);
    }

    @OnClose
    public void onClose(Session session) {
        sessionStorage.removeSession(session);
    }
}