package com.example.statistics.kafka;

import com.example.statistics.service.SessionStorage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

@Slf4j
@Configuration
public class KafkaServiceMapper {
    @Autowired
    private SessionStorage sessionStorage;

    @KafkaListener(topics = "team-stats", containerFactory = "kafkaListenerContainerFactoryDouble")
    public void subscribe(double ratio) {
        sessionStorage.writeForAll(ratio);
    }
}
