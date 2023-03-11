package com.project.services.statistics.kafka.services;

import com.project.services.statistics.data.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@Configuration
@RequiredArgsConstructor
public class SuperStats {
    private final Ranking topWinners = new Ranking(10);
    private final TeamStats stats = new TeamStats();


    @KafkaListener(
            topics = "fights",
            containerFactory = "kafkaListenerContainerFactoryFightToDouble",
            groupId = "fight_team_stats_group")
    @SendTo("team_stats")
    public Double computeTeamStats(Fight result) {
        log.info("Fight received. Computed the team statistics");
        return stats.add(result);
    }

    @KafkaListener(
            topics = "fights",
            containerFactory = "kafkaListenerContainerFactoryFightToScoreDTO",
            groupId = "fight_winner_stats_group")
    @SendTo("winner_stats")
    // Kafka do not allow to pass iterable as one message
    // org.springframework.kafka.listener.adapter.MessagingMessageListenerAdapter.sendResponse
    public ScoreListDTO computeTopWinners(Fight fight) {
        log.info("Fight received. Computed the top winners");
        var s = new Score(fight.getWinnerName());
        List<Score> scores = topWinners.onNewScore(s);
        return new ScoreListDTO(scores);
    }
}
