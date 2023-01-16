package com.example.rest_fight.data;


import com.example.rest_fight.client.Hero;
import com.example.rest_fight.client.Villain;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "A fight between a hero and a villain")
public class Fighters {
    @JsonProperty("hero")
    @NotNull
    private Hero hero;
    @JsonProperty("villain")
    @NotNull
    private Villain villain;
}
