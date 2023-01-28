package com.example.statistics.sockets;

import com.example.statistics.data.ScoreListDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class WinnersStatsWebSocketHandler extends AbstractWebSocketHandler {
    private final ObjectMapper mapper;
    private final List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        sessions.remove(session);
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessions.add(session);
    }

    @KafkaListener(
            topics = "winner_stats",
            containerFactory = "kafkaListenerContainerFactoryScoreDTO",
            groupId = "winner_stats_group")
    public void subscribe(ScoreListDTO list) throws IOException {
        this.writeForAll(mapper.writeValueAsString(list.getScores()));
    }

    public void writeForAll(String serialized) throws IOException {
        for (WebSocketSession session : sessions) {
            write(session, serialized);
        }
    }

    private void write(WebSocketSession session, String serialized) throws IOException {
        session.sendMessage(new TextMessage(serialized));
    }
}