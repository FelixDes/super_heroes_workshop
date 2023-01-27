package com.example.statistics.data;

import lombok.Data;

@Data
public class Score {
    private String name;
    private int score;

    public Score() {
        this.score = 0;
    }
}
