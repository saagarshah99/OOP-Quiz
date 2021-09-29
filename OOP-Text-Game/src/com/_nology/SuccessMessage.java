package com._nology;

//generating a random success message if the user answers correctly
public class SuccessMessage implements GeneratedMessage {
    private final String[] possibleMessages = {
        "Correct!",
        "Awesome!",
        "Sound as a pound!"
    };

    private final String generatedResponse;

    public SuccessMessage() {
        this.generatedResponse = possibleMessages[Utils.randomNumber(0, possibleMessages.length)];
    }

    public String getResponse() {
        return this.generatedResponse;
    }
}
