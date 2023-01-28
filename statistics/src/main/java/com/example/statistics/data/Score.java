package com.example.statistics.data;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(exclude = {"score"})
public class Score {
    private String name;
    private int score;

    public Score() {
        this.score = 1;
    }

    public Score(String name) {
        this.name = name;
        this.score = 1;
    }

    public void incrementScore(int val) {
        score += val;
    }
}
