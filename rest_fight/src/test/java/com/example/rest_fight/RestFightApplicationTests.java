package com.example.rest_fight;

import com.example.rest_fight.client.Hero;
import com.example.rest_fight.client.Villain;
import com.example.rest_fight.data.Fighters;
import com.example.rest_fight.proxies.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.http.HttpHeaders.ACCEPT;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
class RestFightApplicationTests {
    private static final String JSON = MediaType.APPLICATION_JSON_VALUE;
    private static final String DEFAULT_WINNER_NAME = "DEFAULT_WINNER_NAME";
    private static final String DEFAULT_WINNER_PICTURE = "DEFAULT_WINNER_PICTURE";
    private static final int DEFAULT_WINNER_LEVEL = 42;
    private static final String DEFAULT_LOSER_NAME = "DEFAULT_LOSER_NAME";
    private static final String DEFAULT_LOSER_PICTURE = "DEFAULT_LOSER_PICTURE";
    private static final int DEFAULT_LOSER_LEVEL = 6;

    private static final int NB_FIGHTS = 3;
    private static String fightId;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private VillainProxy villainProxy;
    @MockBean
    private HeroProxy heroProxy;
    @Autowired
    private MockVillainProxy mockVillainProxy;
    @Autowired
    private MockHeroProxy mockHeroProxy;

    @Test
    void shouldNotGetUnknownFight() throws Exception {
        Long randomId = new Random().nextLong();
        this.mockMvc.perform(get("/api/fights/{id}", randomId))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldNotAddInvalidItem() throws Exception {
        Fighters fighters = new Fighters();
        fighters.setHero(null);
        fighters.setVillain(null);

        this.mockMvc.perform(post("/api/fights")
                        .header(CONTENT_TYPE, JSON)
                        .header(ACCEPT, JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(1)
    void shouldGetInitialItems() throws Exception {
        JSONArray fights = new JSONArray(this.mockMvc.perform(get("/api/fights"))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString());

        assertThat(fights.length()).isEqualTo(NB_FIGHTS);
    }

    @Test
    @Order(2)
    void shouldAddAnItem() throws Exception {
        Hero hero = new Hero();
        hero.setName(DEFAULT_WINNER_NAME);
        hero.setPicture(DEFAULT_WINNER_PICTURE);
        hero.setLevel(DEFAULT_WINNER_LEVEL);
        Villain villain = new Villain();
        villain.setName(DEFAULT_LOSER_NAME);
        villain.setPicture(DEFAULT_LOSER_PICTURE);
        villain.setLevel(DEFAULT_LOSER_LEVEL);
        Fighters fighters = new Fighters();
        fighters.setHero(hero);
        fighters.setVillain(villain);

        fightId = new JSONObject(this.mockMvc.perform(post("/api/fights")
                        .header(CONTENT_TYPE, JSON)
                        .header(ACCEPT, JSON)
                        .content(mapper.writeValueAsString(fighters)))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("winner")))
                .andExpect(content().string(containsString("loser")))
                .andReturn().getResponse().getContentAsString()).getString("id");

        assertThat(fightId).isNotNull();


        var body = new JSONObject(this.mockMvc.perform(get("/api/fights/{id}", fightId))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString());

        assertThat(body.get("winnerName")).isEqualTo(DEFAULT_WINNER_NAME);
        assertThat(body.get("winnerPicture")).isEqualTo(DEFAULT_WINNER_PICTURE);
        assertThat(body.get("winnerLevel")).isEqualTo(DEFAULT_WINNER_LEVEL);
        assertThat(body.get("loserName")).isEqualTo(DEFAULT_LOSER_NAME);
        assertThat(body.get("loserPicture")).isEqualTo(DEFAULT_LOSER_PICTURE);
        assertThat(body.get("loserLevel")).isEqualTo(DEFAULT_LOSER_LEVEL);
        assertThat(body.get("fightDate")).isNotNull();

        var count = new JSONArray(this.mockMvc.perform(get("/api/fights")).andExpect(status().isOk()).andReturn().getResponse().getContentAsString()).length();
        assertThat(count).isEqualTo(NB_FIGHTS + 1);
    }

    @Test
    void shouldGetRandomFighters() throws Exception {
        Mockito.when(heroProxy.getRandom()).thenReturn(mockHeroProxy.getRandom());
        Mockito.when(villainProxy.getRandom()).thenReturn(mockVillainProxy.getRandom());

        var body = new JSONObject(this.mockMvc.perform(get("/api/fights/randomfighters"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString());

        assertThat(body.getJSONObject("hero").getString("name")).isEqualTo(MockHeroProxy.DEFAULT_HERO_NAME);
        assertThat(body.getJSONObject("hero").getString("picture")).isEqualTo(MockHeroProxy.DEFAULT_HERO_PICTURE);
        assertThat(body.getJSONObject("hero").getInt("level")).isEqualTo(MockHeroProxy.DEFAULT_HERO_LEVEL);
        assertThat(body.getJSONObject("villain").getString("name")).isEqualTo(MockVillainProxy.DEFAULT_VILLAIN_NAME);
        assertThat(body.getJSONObject("villain").getString("picture")).isEqualTo(MockVillainProxy.DEFAULT_VILLAIN_PICTURE);
        assertThat(body.getJSONObject("villain").getInt("level")).isEqualTo(MockVillainProxy.DEFAULT_VILLAIN_LEVEL);
    }
}
