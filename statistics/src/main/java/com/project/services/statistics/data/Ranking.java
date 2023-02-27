package com.project.services.statistics.data;

import java.util.*;

public class Ranking {
    private final int max;

    private final Comparator<Score> comparator = Comparator.comparingInt(s -> -1 * s.getScore());

    private final LinkedList<Score> top = new LinkedList<>();

    public Ranking(int size) {
        max = size;
    }

    public List<Score> onNewScore(Score score) {
        int i = top.indexOf(score);
        if (i >= 0) {
            top.get(i).incrementScore(score.getScore());
        } else {
            top.add(score);
        }
        top.sort(comparator);

        if (top.size() > max) {
            top.removeLast();
        }

        return Collections.unmodifiableList(top);
    }
}