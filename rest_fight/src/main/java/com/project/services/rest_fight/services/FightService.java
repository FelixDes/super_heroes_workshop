package com.project.services.rest_fight.services;

import com.project.services.rest_fight.client.Hero;
import com.project.services.rest_fight.client.Villain;
import com.project.services.rest_fight.data.Fight;
import com.project.services.rest_fight.data.FightRepository;
import com.project.services.rest_fight.data.Fighters;
import com.project.services.rest_fight.proxies.Proxyable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Random;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class FightService {
    private final FightRepository repo;
    private final Proxyable<Hero> heroProxy;
    private final Proxyable<Villain> villainProxy;
    private final Random random = new Random();
    private final KafkaTemplate<String, Fight> kafkaTemplate;

    public List<Fight> findAllFights() {
        return repo.findAll();
    }

    public Fight findFightById(Long id) {
        var optFight = repo.findById(id);
        return optFight.orElse(null);
    }

    @Transactional
    public Fighters findRandomFighters() {
//        return null;
        Hero hero = findRandomHero();
        Villain villain = findRandomVillain();
        Fighters fighters = new Fighters();
        fighters.setHero(hero);
        fighters.setVillain(villain);
        return fighters;
    }

    Villain findRandomVillain() {
        return villainProxy.getRandom();
    }

    Hero findRandomHero() {
        return heroProxy.getRandom();
    }

    @Transactional
    public Fight persistFight(Fighters fighters) {
        Fight fight;

        int heroAdjust = random.nextInt(20);
        int villainAdjust = random.nextInt(20);

        if ((fighters.getHero().getLevel() + heroAdjust) > (fighters.getVillain().getLevel() + villainAdjust)) {
            fight = heroWon(fighters);
        } else if (fighters.getHero().getLevel() < fighters.getVillain().getLevel()) {
            fight = villainWon(fighters);
        } else {
            fight = random.nextBoolean() ? heroWon(fighters) : villainWon(fighters);
        }

        fight.setFightDate(Instant.now());
        repo.saveAndFlush(fight);

        kafkaTemplate.sendDefault(fight).whenComplete((res, e) -> log.info(res.toString()));

        return fight;
    }

    private Fight heroWon(Fighters fighters) {
        log.debug("Hero won");
        Fight fight = new Fight();
        fight.setWinnerName(fighters.getHero().getName());
        fight.setWinnerPicture(fighters.getHero().getPicture());
        fight.setWinnerLevel(fighters.getHero().getLevel());
        fight.setLoserName(fighters.getVillain().getName());
        fight.setLoserPicture(fighters.getVillain().getPicture());
        fight.setLoserLevel(fighters.getVillain().getLevel());
        fight.setWinnerTeam(Fight.Teams.HEROES.getString());
        fight.setLoserTeam(Fight.Teams.VILLAINS.getString());
        return fight;
    }

    private Fight villainWon(Fighters fighters) {
        log.debug("Villain won");
        Fight fight = new Fight();
        fight.setWinnerName(fighters.getVillain().getName());
        fight.setWinnerPicture(fighters.getVillain().getPicture());
        fight.setWinnerLevel(fighters.getVillain().getLevel());
        fight.setLoserName(fighters.getHero().getName());
        fight.setLoserPicture(fighters.getHero().getPicture());
        fight.setLoserLevel(fighters.getHero().getLevel());
        fight.setWinnerTeam(Fight.Teams.VILLAINS.getString());
        fight.setLoserTeam(Fight.Teams.HEROES.getString());
        return fight;
    }
}
