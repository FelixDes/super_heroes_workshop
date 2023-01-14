package com.example.rest_villains;

import com.example.rest_villains.data.Villain;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpHeaders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
class RestVillainsApplicationTests {
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
    private static String villainId;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldPingOpenAPI() throws Exception {
        this.mockMvc.perform(get("/v3/api-docs")).andExpect(status().isOk());
    }

    @Test
    void shouldNotGetUnknownVillain() throws Exception {
        Long randomId = new Random().nextLong();
        this.mockMvc.perform(get("/api/villains/{id}", String.valueOf(randomId)))
                .andExpect(status().isNoContent());

    }

    @Test
    void shouldGetRandomVillain() throws Exception {
        this.mockMvc.perform(get("/api/villains/random"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldNotAddInvalidItem() throws Exception {
        Villain villain = new Villain();
        villain.setName(null);
        villain.setOtherName(DEFAULT_OTHER_NAME);
        villain.setLevel(DEFAULT_LEVEL);
        villain.setPowers(DEFAULT_POWERS);
        villain.setPicture(DEFAULT_PICTURE);

        this.mockMvc.perform(post("/api/villains")
                        .content(villain.toString())
                        .header(CONTENT_TYPE, JSON)
                        .header(ACCEPT, JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldGetInitialItems() throws Exception {
        this.mockMvc.perform(get("/api/villains"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(JSON));
    }

    @Test
    @Order(1)
    void shouldAddAnItem() throws Exception {
        Villain villain = new Villain();
        villain.setName(DEFAULT_NAME);
        villain.setOtherName(DEFAULT_OTHER_NAME);
        villain.setLevel(DEFAULT_LEVEL);
        villain.setPowers(DEFAULT_POWERS);
        villain.setPicture(DEFAULT_PICTURE);
        var villainJson = villain.toString();

        String location = this.mockMvc.perform(post("/api/villains")
                        .content(villainJson)
                        .header(CONTENT_TYPE, JSON)
                        .header(ACCEPT, JSON))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getHeader(LOCATION);

        assertThat(location).contains("/api/villains");

        //id
        String[] segments = location.split("/");
        villainId = segments[segments.length - 1];

        assertThat(villainId).isNotNull();

        this.mockMvc.perform(get("/api/villains/{id}", villainId))
                .andExpect(status().isOk())
                .andExpect(content().json(villainJson));
    }

    @Test
    @Order(2)
    void testUpdatingAnItem() throws Exception {

        Villain villain = new Villain();
        villain.setId(Long.valueOf(villainId));
        villain.setName(UPDATED_NAME);
        villain.setOtherName(UPDATED_OTHER_NAME);
        villain.setLevel(UPDATED_LEVEL);
        villain.setPowers(UPDATED_POWERS);
        villain.setPicture(UPDATED_PICTURE);
        var villainJson = villain.toString();

        this.mockMvc.perform(put("/api/villains")
                        .content(villainJson)
                        .header(CONTENT_TYPE, JSON)
                        .header(ACCEPT, JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(JSON))
                .andExpect(content().json(villainJson));
    }

    @Test
    @Order(3)
    void shouldRemoveAnItem() throws Exception {
        this.mockMvc.perform(delete("/api/villains/{id}", String.valueOf(villainId)))
                .andExpect(status().isNoContent());
    }
}
