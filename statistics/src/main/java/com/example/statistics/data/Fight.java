package com.example.statistics.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.Instant;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Fight {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private long id;
    private Instant fightDate;
    private String winnerName;
    private int winnerLevel;
    private String winnerPicture;
    private String loserName;
    private int loserLevel;
    private String loserPicture;
    private String winnerTeam;
    private String loserTeam;
}