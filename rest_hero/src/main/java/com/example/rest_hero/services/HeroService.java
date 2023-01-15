package com.example.rest_hero.services;

import com.example.rest_hero.data.Hero;
import com.example.rest_hero.data.HeroRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Random;


@Service
@Transactional
public class HeroService {
    private final HeroRepository heroRepository;


    public HeroService(HeroRepository heroRepository) {
        this.heroRepository = heroRepository;
    }

    @Transactional
    public Flux<Hero> findAllHeroes() {
        return heroRepository.findAll();
    }

    @Transactional
    public Mono<Hero> findHeroById(Long id) {
        return heroRepository.findById(id);
    }

    @Transactional
    public Mono<Hero> findRandomHero() {
        Random random = new Random();
        return heroRepository.count()
                .map(count -> {
                    var c = count.intValue();
                    if (c > 0) {
                        return random.nextInt(0, c);
                    } else {
                        return 0;
                    }
                })
                .flatMap(num -> heroRepository.findAll().skip(num).next());
    }

    @Transactional
    public Mono<Hero> saveHero(@Valid Hero hero) {
        return heroRepository.save(hero);
    }

    @Transactional
    public Mono<Hero> updateHero(@Valid Hero hero) {
        return heroRepository.findById(hero.getId()).map(oldHero -> {
            oldHero.setName(hero.getName());
            oldHero.setOtherName(hero.getOtherName());
            oldHero.setLevel(hero.getLevel());
            oldHero.setPicture(hero.getPicture());
            oldHero.setPowers(hero.getPowers());
            return oldHero;
        });
    }

    @Transactional
    public Mono<Void> deleteHeroById(Long id) {
        return heroRepository.deleteById(id);
    }
}
