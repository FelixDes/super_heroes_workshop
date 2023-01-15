package com.example.rest_fight.client;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description="The hero fighting against the villain")
public class Hero {
    @NotNull
    public String name;
    @NotNull
    public int level;
    @NotNull
    public String picture;
    @JsonIgnore
    public String powers;
}