package com._nology;

//generating a random success message if the user answers correctly and returning updated score
public class SuccessMessage implements GeneratedMessage {
    private String pointStr;
    private String generatedResponse;
    private final String[] possibleMessages = {
        "Correct!",
        "Awesome!",
        "Sound as a pound!"
    };

    public SuccessMessage(Player player) {
        this.generatedResponse = possibleMessages[Utils.randomNumber(0, possibleMessages.length)];

        String points = " (+" + player.getScore() + (player.getScore() != 1 ? " points" : " point") + ")";
        this.pointStr = points + "\n" + player.getScoreStr();
    }

    public String getResponse() {
        return this.generatedResponse + this.pointStr;
    }
}
