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
    public Hero hero;
    @JsonProperty("villain")
    @NotNull
    public Villain villain;

//    @Data
//    @Schema(description = "The villain fighting against the hero")
//    public static class Villain {
//        @NotNull
//        public String name;
//        @NotNull
//        public int level;
//        @NotNull
//        public String picture;
//        @JsonIgnore
//        public String powers;
//    }
//
//    @Data
//    @Schema(description = "The hero fighting against the villain")
//    public static class Hero {
//        @NotNull
//        public String name;
//        @NotNull
//        public int level;
//        @NotNull
//        public String picture;
//        @JsonIgnore
//        public String powers;
//    }
}
