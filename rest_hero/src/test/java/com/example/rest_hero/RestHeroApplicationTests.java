package com.example.rest_hero;

import com.example.rest_hero.controllers.HeroController;
import com.example.rest_hero.data.Hero;
import com.example.rest_hero.services.HeroService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpHeaders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
//@SpringBootTest
//@AutoConfigureMockMvc
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//@DirtiesContext
@RunWith(SpringRunner.class)
@WebFluxTest(HeroController.class)
@DirtiesContext
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
    private static String heroId;

//    @Autowired
//    private MockMvc mockMvc;

    @Autowired
    WebTestClient webTestClient;

    @MockBean
    private HeroService service;

//    @Test
//    void shouldPingOpenAPI(){
//        webTestClient.get().uri("/v3/api-docs").exchange().expectStatus().isOk();
////        this.mockMvc.perform(get("/v3/api-docs")).andExpect(status().isOk());
//    }

//    @Test
//    void shouldNotGetUnknownHero() throws Exception {
//        Long randomId = new Random().nextLong();
//        webTestClient.get().uri("/api/heroes/{id}", String.valueOf(randomId)).exchange().expectStatus().isNoContent();
////        this.mockMvc.perform(get("/api/heroes/{id}", String.valueOf(randomId)))
////                .andExpect(status().isNoContent());
//
//    }
//
//    @Test
//    void shouldGetRandomHero() throws Exception {
//        this.mockMvc.perform(get("/api/heroes/random"))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    void shouldNotAddInvalidItem() throws Exception {
//        Hero Hero = new Hero();
//        Hero.setName(null);
//        Hero.setOtherName(DEFAULT_OTHER_NAME);
//        Hero.setLevel(DEFAULT_LEVEL);
//        Hero.setPowers(DEFAULT_POWERS);
//        Hero.setPicture(DEFAULT_PICTURE);
//
//        this.mockMvc.perform(post("/api/heroes")
//                        .content(Hero.toString())
//                        .header(CONTENT_TYPE, JSON)
//                        .header(ACCEPT, JSON))
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    void shouldGetInitialItems() throws Exception {
//        this.mockMvc.perform(get("/api/heroes")).andDo((h) -> {
//                    log.info(h.toString());
//                })
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(JSON));
//    }
//
//    @Test
//    @Order(1)
//    void shouldAddAnItem() throws Exception {
//        Hero hero = new Hero();
//        hero.setName(DEFAULT_NAME);
//        hero.setOtherName(DEFAULT_OTHER_NAME);
//        hero.setLevel(DEFAULT_LEVEL);
//        hero.setPowers(DEFAULT_POWERS);
//        hero.setPicture(DEFAULT_PICTURE);
//        var heroJson = hero.toString();
//
//        String location = this.mockMvc.perform(post("/api/heroes")
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
//    }
//
//    @Test
//    @Order(2)
//    void testUpdatingAnItem() throws Exception {
//
//        Hero Hero = new Hero();
//        Hero.setId(Long.valueOf(heroId));
//        Hero.setName(UPDATED_NAME);
//        Hero.setOtherName(UPDATED_OTHER_NAME);
//        Hero.setLevel(UPDATED_LEVEL);
//        Hero.setPowers(UPDATED_POWERS);
//        Hero.setPicture(UPDATED_PICTURE);
//        var HeroJson = Hero.toString();
//
//        this.mockMvc.perform(put("/api/heroes")
//                        .content(HeroJson)
//                        .header(CONTENT_TYPE, JSON)
//                        .header(ACCEPT, JSON))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(JSON))
//                .andExpect(content().json(HeroJson));
//    }
//
//    @Test
//    @Order(3)
//    void shouldRemoveAnItem() throws Exception {
//        this.mockMvc.perform(delete("/api/heroes/{id}", String.valueOf(heroId)))
//                .andExpect(status().isNoContent());
//    }
}
