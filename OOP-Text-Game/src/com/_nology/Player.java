package com._nology;

import java.io.IOException;

public class Player extends Quiz {
    private final String name;
    private int score = 0;

    public Player(int numberOfQuestions, String name) throws IOException {
        super(numberOfQuestions);
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public int getScore() {
        return this.score;
    }

    public String getScoreStr() {
        return "Score: " + getScore() + " points";
    }

    public void setScore(int newScore) {
        this.score += newScore;
    }
}