package com.example.rest_hero;

import com.example.rest_hero.data.Hero;
import com.example.rest_hero.data.HeroRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.test.annotation.DirtiesContext;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Objects;

@Slf4j
@DirtiesContext
@DataR2dbcTest
class RestHeroServiceTests {
    @Autowired
    private HeroRepository repo;

    private static final String DEFAULT_NAME = "DEFAULT_NAME";
    private static final String UPDATED_NAME = "DEFAULT_NAME (updated)";
    private static final String DEFAULT_OTHER_NAME = "DEFAULT_OTHER_NAME";
    private static final String UPDATED_OTHER_NAME = "DEFAULT_OTHER_NAME (updated)";
    private static final String DEFAULT_PICTURE = "DEFAULT_PICTURE";
    private static final String UPDATED_PICTURE = "UPDATED_PICTURE";
    private static final String DEFAULT_POWERS = "DEFAULT_POWERS";
    private static final String UPDATED_POWERS = "UPDATED_POWERS (updated)";
    private static final int DEFAULT_LEVEL = 42;
    private static final int UPDATED_LEVEL = 43;
    private static long count;
    private static Hero defaultHero = new Hero(null, DEFAULT_NAME, DEFAULT_OTHER_NAME, DEFAULT_LEVEL, DEFAULT_PICTURE, DEFAULT_POWERS);

    @BeforeEach
    public void setup() throws InterruptedException {
        long i = 1;
        Flux<Hero> deleteAndInsert = repo.deleteAll()
                .thenMany(repo.saveAll(Flux.just(
                        defaultHero,
                        new Hero(null, DEFAULT_NAME + i, DEFAULT_OTHER_NAME + i, DEFAULT_LEVEL, DEFAULT_PICTURE + i, DEFAULT_POWERS + i++),
                        new Hero(null, DEFAULT_NAME + i, DEFAULT_OTHER_NAME + i, DEFAULT_LEVEL, DEFAULT_PICTURE + i, DEFAULT_POWERS + i++),
                        new Hero(null, DEFAULT_NAME + i, DEFAULT_OTHER_NAME + i, DEFAULT_LEVEL, DEFAULT_PICTURE + i, DEFAULT_POWERS + i++)
                )));
        count = i;
        StepVerifier.create(deleteAndInsert).expectNextCount(i).verifyComplete();
    }

    @Test
    public void shouldDeleteAll() {
        StepVerifier.create(repo.deleteAll()).verifyComplete();
    }

    @Test
    public void shouldFindAllHeroes() {
        StepVerifier.create(repo.findAll()).expectNextCount(count).verifyComplete();
    }

    @Test
    public void shouldFindHeroById() {
        StepVerifier.create(repo.findById(Objects.requireNonNull(repo.findAll().blockFirst()).getId()))
                .expectNextMatches(h -> h.equals(defaultHero)).verifyComplete();
    }

    @Test
    public void shouldDeleteHeroById() {
        StepVerifier.create(repo.deleteById(Objects.requireNonNull(repo.findAll().blockLast()).getId())).verifyComplete();
        StepVerifier.create(repo.findAll()).expectNextCount(count - 1).verifyComplete();
    }
}
