package com._nology;

public class Player {
    private String name;
    private int score = 0;

    public Player(String name) {
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
