package com.project.services.rest_fight.client;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "The villain fighting against the hero")
public class Villain {
    @NotNull
    private String name;
    @NotNull
    private int level;
    @NotNull
    private String picture;
    @JsonIgnore
    private String powers;
}
