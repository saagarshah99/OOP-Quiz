package com._nology;

//generating a random fail message if the user answers incorrectly
public class FailMessage implements GeneratedMessage {
    private final String[] possibleMessages = {
        "Incorrect!",
        "Better luck next time!",
        "You suck!"
    };

    private final String generatedResponse;

    public FailMessage() {
        this.generatedResponse = possibleMessages[Utils.randomNumber(0, possibleMessages.length)];
    }

    public String getResponse() {
        return this.generatedResponse;
    }
}
