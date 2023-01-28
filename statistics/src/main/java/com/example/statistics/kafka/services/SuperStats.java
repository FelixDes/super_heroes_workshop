package com.example.statistics.kafka.services;

import com.example.statistics.data.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class SuperStats {
    private final Ranking topWinners = new Ranking(10);
    private final TeamStats stats = new TeamStats();

    @KafkaListener(
            topics = "fights",
            containerFactory = "kafkaListenerContainerFactoryFightToDouble",
            groupId = "fight_team_stats_group")
    @SendTo("team-stats")
    public Double computeTeamStats(Fight result) {
        log.info("Fight received. Computed the team statistics");
        return stats.add(result);
    }

    @KafkaListener(
            topics = "fights",
            containerFactory = "kafkaListenerContainerFactoryFightToScoreDTO",
            groupId = "fight_winner_stats_group")
    @SendTo("winner-stats")
    // Kafka do not allow to pass iterable as one message
    // org.springframework.kafka.listener.adapter.MessagingMessageListenerAdapter.sendResponse
    public ScoreListDTO computeTopWinners(Fight result) {
        log.info("Fight received. Computed the top winners");
        List<Score> scores = topWinners.onNewScore(getScoreForFight(result));
        return new ScoreListDTO(scores);
    }


    private Score getScoreForFight(Fight fight) {
        var score = new Score();
        score.setName(fight.getWinnerName());
        score.setScore(score.getScore() + 1);
        return score;
    }
}
