package com.example.rest_fight.services;

import com.example.rest_fight.client.Hero;
import com.example.rest_fight.client.Villain;
import com.example.rest_fight.data.Fight;
import com.example.rest_fight.data.FightRepository;
import com.example.rest_fight.data.Fighters;
import com.example.rest_fight.proxies.Proxyable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

//    @HystrixCommand(fallbackMethod = "fallbackRandomHero")
    Hero findRandomHero() {
        return heroProxy.getRandom();
    }


//    public Villain fallbackRandomVillain() {
//        Villain villain = new Villain();
//        villain.setName("Fallback villain");
//        villain.setPicture("https://dummyimage.com/280x380/b22222/ffffff&text=Fallback+Villain");
//        villain.setPowers("Fallback villain powers");
//        villain.setLevel(42);
//        return villain;
//    }

//    public Hero fallbackRandomHero() {
//        Hero hero = new Hero();
//        hero.setName("Fallback hero");
//        hero.setPicture("https://dummyimage.com/280x380/1e8fff/ffffff&text=Fallback+Hero");
//        hero.setPowers("Fallback hero powers");
//        hero.setLevel(1);
//        return hero;
//    }

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
