package com.example.statistics.data;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class Ranking {

    private final int max;

    private final Comparator<Score> comparator = Comparator.comparingInt(s -> -1 * s.getScore());

    private final LinkedList<Score> top = new LinkedList<>();

    public Ranking(int size) {
        max = size;
    }

    public List<Score> onNewScore(Score score) {
        // Remove score if already present,
        top.removeIf(s -> s.getName().equalsIgnoreCase(score.getName()));
        // Add the score
        top.add(score);
        // Sort
        top.sort(comparator);

        // Drop on overflow
        if (top.size() > max) {
            top.remove(top.getLast());
        }

        return Collections.unmodifiableList(top);
    }
}