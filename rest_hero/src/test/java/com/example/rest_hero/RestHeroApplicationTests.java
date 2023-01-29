package com.example.rest_hero;

import com.example.rest_hero.controllers.HeroController;
import com.example.rest_hero.data.Hero;
import com.example.rest_hero.data.HeroRepository;
import com.example.rest_hero.services.HeroService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.hamcrest.Matchers.*;
import static org.springframework.http.HttpHeaders.ACCEPT;

@Slf4j
@WebFluxTest(HeroController.class)
@Import(HeroService.class)
@RunWith(SpringRunner.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RestHeroApplicationTests {
    private static final String JSON = MediaType.APPLICATION_JSON_VALUE;
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

    @Autowired
    WebTestClient webTestClient;

    @MockBean
    HeroRepository repository;

    @Autowired
    ObjectMapper mapper;

//    @Test
//    void shouldPingOpenAPI() {
//        webTestClient.get().uri("/v3/api-docs").exchange().expectStatus().isOk();
//    }

    @Test
    void shouldNotGetUnknownHero() {
        Long randomId = new Random().nextLong();

        Mockito.when(repository.findById(randomId)).thenReturn(Mono.empty());

        webTestClient
                .get()
                .uri("/api/heroes/{id}", String.valueOf(randomId))
                .exchange()
                .expectStatus().isNoContent()
                .expectBody(Hero.class)
                .value(equalTo(null));
    }

    @Test
    void shouldGetRandomHero() {
        Hero hero1 = new Hero();
        hero1.setName(DEFAULT_NAME);
        hero1.setOtherName(DEFAULT_OTHER_NAME);
        hero1.setLevel(DEFAULT_LEVEL);
        hero1.setPowers(DEFAULT_POWERS);
        hero1.setPicture(DEFAULT_PICTURE);

        Mockito.when(repository.count()).thenReturn(Mono.just(1L));
        Mockito.when(repository.findAll()).thenReturn(Flux.just(hero1));

        webTestClient
                .get()
                .uri("/api/heroes/random")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Hero.class)
                .value(Hero::getName, equalTo(DEFAULT_NAME));
    }


    @Test
    void shouldNotAddInvalidItem() {
        Hero hero = new Hero();
        hero.setName(null);
        hero.setOtherName(DEFAULT_OTHER_NAME);
        hero.setLevel(DEFAULT_LEVEL);
        hero.setPowers(DEFAULT_POWERS);
        hero.setPicture(DEFAULT_PICTURE);

        webTestClient
                .post()
                .uri("/api/heroes")
                .body(BodyInserters.fromValue(hero))
                .exchange()
                .expectStatus().is5xxServerError();
    }

    @Test
    void shouldGetInitialItems() {
        Hero hero1 = new Hero();
        hero1.setName(DEFAULT_NAME);
        hero1.setOtherName(DEFAULT_OTHER_NAME);
        hero1.setLevel(DEFAULT_LEVEL);
        hero1.setPowers(DEFAULT_POWERS);
        hero1.setPicture(DEFAULT_PICTURE);

        Hero hero2 = new Hero();
        hero2.setName(DEFAULT_NAME);
        hero2.setOtherName(DEFAULT_OTHER_NAME);
        hero2.setLevel(DEFAULT_LEVEL);
        hero2.setPowers(DEFAULT_POWERS);
        hero2.setPicture(DEFAULT_PICTURE);

        List<Hero> list = new ArrayList<>();
        list.add(hero1);
        list.add(hero2);

        Flux<Hero> flux = Flux.fromIterable(list);

        Mockito.when(repository.findAll()).thenReturn(flux);

        webTestClient
                .get()
                .uri("/api/heroes")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Hero.class)
                .value(v -> v.get(1).getName(), equalTo(DEFAULT_NAME));
    }

    @Test
    @Order(1)
    void shouldAddAnItem() throws JsonProcessingException {
        Hero hero = new Hero();
        hero.setId(1L);
        hero.setName(DEFAULT_NAME);
        hero.setOtherName(DEFAULT_OTHER_NAME);
        hero.setLevel(DEFAULT_LEVEL);
        hero.setPowers(DEFAULT_POWERS);
        hero.setPicture(DEFAULT_PICTURE);
        String heroJsonString = mapper.writeValueAsString(hero);


        Mockito.when(repository.save(Mockito.any(Hero.class))).thenReturn(Mono.just(hero));

        webTestClient
                .post()
                .uri("/api/heroes")
//                .contentType(MediaType.APPLICATION_JSON)
//                .header(ACCEPT, JSON)
                .body(BodyInserters.fromValue(hero))
                .exchange()
                .expectStatus().isCreated();
//                .returnResult(Hero.class).getResponseHeaders().getLocation().toString();
//        System.out.println(location);
//                this.mockMvc.perform(post("/api/heroes")
//                        .header(CONTENT_TYPE, JSON)
//                        .header(ACCEPT, JSON)
//                        .content(heroJson))
//                .andExpect(status().isCreated())
//                .andReturn().getResponse().getHeader(LOCATION);
//
//        assertThat(location).contains("/api/heroes");
//
//        //id
//        String[] segments = location.split("/");
//        heroId = segments[segments.length - 1];
//
//        assertThat(heroId).isNotNull();
//
//        this.mockMvc.perform(get("/api/heroes/{id}", heroId))
//                .andExpect(status().isOk())
//                .andExpect(content().json(heroJson));
    }

    @Test
    @Order(2)
    void testUpdatingAnItem() throws JsonProcessingException {

        Hero defaultHero = new Hero();
        defaultHero.setId(1L);
        defaultHero.setName(DEFAULT_NAME);
        defaultHero.setOtherName(DEFAULT_OTHER_NAME);
        defaultHero.setLevel(DEFAULT_LEVEL);
        defaultHero.setPowers(DEFAULT_POWERS);
        defaultHero.setPicture(DEFAULT_PICTURE);

        Mockito.when(repository.findById(1L)).thenReturn(Mono.just(defaultHero));

        Hero updatedHero = new Hero();
        updatedHero.setId(1L);
        updatedHero.setName(UPDATED_NAME);
        updatedHero.setOtherName(UPDATED_OTHER_NAME);
        updatedHero.setLevel(UPDATED_LEVEL);
        updatedHero.setPowers(UPDATED_POWERS);
        updatedHero.setPicture(UPDATED_PICTURE);

        webTestClient
                .put()
                .uri("/api/heroes")
                .body(BodyInserters.fromValue(updatedHero))
                .exchange()
                .expectStatus().isOk()
                .expectBody(Hero.class)
                .isEqualTo(updatedHero);
    }


    @Test
    @Order(3)
    void shouldRemoveAnItem() {
        Mono<Void> voidMono = Mono.just("123").then();

        Mockito.when(repository.deleteById(1L)).thenReturn(voidMono);

        webTestClient
                .delete()
                .uri("/api/heroes/{id}", String.valueOf(1L))
                .exchange()
                .expectStatus().isNoContent();

//        this.mockMvc.perform(delete("/api/heroes/{id}", String.valueOf(heroId)))
//                .andExpect(status().isNoContent());
    }
}
