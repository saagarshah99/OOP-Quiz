package com._nology;

//generating a random success message if the user answers correctly and returning updated score
public class SuccessMessage implements GeneratedMessage {
    private final String pointStr;
    private final String generatedResponse;
    private final String[] possibleMessages = {
        "Correct!",
        "Awesome!",
        "Sound as a pound!"
    };

    public SuccessMessage(Player player, int newScore) {
        this.generatedResponse = getMessage();

        String points = " (+" + newScore + (newScore != 1 ? " points" : " point") + ")";
        this.pointStr = points + "\n" + player.getScoreStr();
    }

    public String getResponse() {
        return this.generatedResponse + this.pointStr;
    }

    public String getMessage() {
        return this.possibleMessages[Utils.randomNumber(0, possibleMessages.length)];
    }
}