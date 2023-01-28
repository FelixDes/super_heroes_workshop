package com.example.statistics.sockets;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Slf4j
@Component
public class TeamStatsWebSocketHandler extends AbstractWebSocketHandler {


    private final List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        sessions.remove(session);
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessions.add(session);
    }

    @KafkaListener(topics = "team_stats", containerFactory = "kafkaListenerContainerFactoryDouble")
    public void subscribe(double ratio) throws IOException {
        this.writeForAll(ratio);
    }

    public void writeForAll(double ratio) throws IOException {
        for (WebSocketSession session : sessions) {
            write(session, ratio);
        }
    }

    private void write(WebSocketSession session, Double ratio) throws IOException {
        session.sendMessage(new TextMessage(ratio.toString()));
    }
}