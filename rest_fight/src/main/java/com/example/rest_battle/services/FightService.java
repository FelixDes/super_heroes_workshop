package com.example.rest_battle.services;

import com.example.rest_battle.data.Fight;
import com.example.rest_battle.data.FightRepository;
import com.example.rest_battle.data.Fighters;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Random;

@Slf4j
@Service
@Transactional
public class FightService {
    private final Random random = new Random();

    private final FightRepository repo;

    public FightService(FightRepository repo) {
        this.repo = repo;
    }

    public List<Fight> findAllFights() {
        return repo.findAll();
    }

    public Fight findFightById(Long id) {
        var optFight = repo.findById(id);
        return optFight.orElse(null);
    }

    public Fighters findRandomFighters() {
        // Will be implemented later
        return null;
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

        return fight;
    }

    private Fight heroWon(Fighters fighters) {
        log.info("Hero won");
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
        log.info("Villain won");
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
