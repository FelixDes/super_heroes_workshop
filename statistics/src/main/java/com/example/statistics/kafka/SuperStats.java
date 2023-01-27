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
    public Double computeTeamStats(Fight results) {
        log.info("Fight received. Computed the team statistics");
        return stats.add(results);
    }

//    @KafkaListener(topics = "fights")
//    @SendTo("winner-stats")
//    public Iterable<Score> computeTopWinners(Fight results) {
//        return results
//                .group().by(fight -> fight.winnerName)
//                .onItem().transformToMultiAndMerge(group ->
//                        group.onItem().scan(Score::new, this::incrementScore)
//                )
//                .onItem().transform(topWinners::onNewScore)
//                .invoke(() -> log.info("Fight received. Computed the top winners"));
//    }

    private Score incrementScore(Score score, Fight fight) {
        score.name = fight.getWinnerName();
        score.score = score.score + 1;
        return score;
    }
}
