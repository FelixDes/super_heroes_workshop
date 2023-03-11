package com.project.services.statistics.sockets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketServerConfiguration implements WebSocketConfigurer {

    @Autowired
    protected TeamStatsWebSocketHandler teamStatsWebSocketHandler;

    @Autowired
    protected WinnersStatsWebSocketHandler winnersStatsWebSocketHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(teamStatsWebSocketHandler, "/stats/team");
        registry.addHandler(winnersStatsWebSocketHandler, "/stats/winners");
    }
}