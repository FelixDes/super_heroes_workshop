package com.example.statistics.kafka;

import com.example.statistics.data.Fight;
import com.example.statistics.data.Ranking;
import com.example.statistics.data.Score;
import com.example.statistics.data.TeamStats;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SuperStats {
    private final Ranking topWinners = new Ranking(10);
    private final TeamStats stats = new TeamStats();

    @KafkaListener(topics = "fights", containerFactory = "kafkaListenerContainerFactoryFightToDouble")
    @SendTo("team-stats")
    public Double computeTeamStats(Fight result) {
        log.info("Fight received. Computed the team statistics");
        return stats.add(result);
    }

    @KafkaListener(topics = "fights", containerFactory = "")
    @SendTo("winner-stats")
    public Iterable<Score> computeTopWinners(Fight result) {
        return topWinners.onNewScore(getScoreForFight(result));
//        return results
//                .group().by(fight -> fight.winnerName)
//                .onItem().transformToMultiAndMerge(group ->
//                        group.onItem().scan(Score::new, this::getScoreForFight)
//                )
//                .map(topWinners::onNewScore)
//                .invoke(() -> log.info("Fight received. Computed the top winners"));
    }

    private Score getScoreForFight(Fight fight) {
        var score = new Score();
        score.setName(fight.getWinnerName());
        score.setScore(score.getScore() + 1);
        return score;
    }
}
