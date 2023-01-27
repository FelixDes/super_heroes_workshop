package com.example.statistics.service;

import jakarta.websocket.Session;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Slf4j
@Component
@Scope("singleton")
public class SessionStorage {
    private final List<Session> sessions = new CopyOnWriteArrayList<>();

    public void addSession(Session s) {
        sessions.add(s);
    }

    public void removeSession(Session s) {
        sessions.remove(s);
    }

    public void writeForAll(double ratio) {
        for (Session session : sessions) {
            write(session, ratio);
        }
    }

    private void write(Session session, double ratio) {
        session.getAsyncRemote().sendText(Double.toString(ratio), result -> {
            if (result.getException() != null) {
                log.error("Unable to write message to session", result.getException());
            }
        });
    }
}
