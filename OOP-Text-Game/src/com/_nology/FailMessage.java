package com._nology;

//generating a random fail message if the user answers incorrectly, returning correct answer and score
public class FailMessage implements GeneratedMessage {
    private String pointStr;
    private final String generatedResponse;
    private final String[] possibleMessages = {
        "Incorrect!",
        "Better luck next time!",
        "You suck!"
    };

    public FailMessage(String correctAnswer, String scoreStr) {
        this.generatedResponse = possibleMessages[Utils.randomNumber(0, possibleMessages.length)];
        this.pointStr = " The correct answer was '" + correctAnswer + "'\n" + scoreStr;
    }

    public String getResponse() {
        return this.generatedResponse + pointStr;
    }
}
