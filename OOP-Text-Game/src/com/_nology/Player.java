package com._nology;

public class Player extends Quiz {
    private final String name;
    private int score = 0;

    public Player(String name) {
        super();
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
