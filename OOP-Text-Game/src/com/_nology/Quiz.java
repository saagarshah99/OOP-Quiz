package com._nology;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class Quiz {
    private final String[][] questions;
    private final int numberOfQuestions;

    public Quiz(int numberOfQuestions) throws IOException {
        this.numberOfQuestions = numberOfQuestions;
        this.questions = this.generateQuestions();
    }

    public String[][] getQuestions() {
        return this.questions;
    }

    //fetching questions from api and storing them in array
    private String[][] generateQuestions() throws IOException {
        String[][] questions = new String[this.numberOfQuestions][4];

        for (int i = 0; i < questions.length; i++) {
            String[] newQuestion = extractJSONQuestion();

            questions[i][0] = newQuestion[0]; //question
            questions[i][1] = newQuestion[1]; //answer
            questions[i][2] = newQuestion[2]; //difficulty
            questions[i][3] = newQuestion[3]; //incorrect answers
        }

        return questions;
    }

    //removing unwanted characters from json question data extracted from api
    private String cleanJSON(String jsonStr, String key1, String key2) {
        String sanitisedStr = jsonStr.substring(jsonStr.indexOf(key1), jsonStr.indexOf(key2));

        if(key1.equalsIgnoreCase("incorrect_answers")) {
            for(String section : new String[] {"incorrect_answers\":[", "}", "]", "\""}) {
                sanitisedStr = sanitisedStr.replace(section, "");
            }
        }
        else {
            for(String section : new String[] {"\"", ",", key1 + ":"}) {
                sanitisedStr = sanitisedStr.replace(section, "");
            }
        }

        return sanitisedStr.replace("&quot;", "'").replace("&#039;", "'").trim();
    }

    //fetching questions from api https://www.baeldung.com/httpurlconnection-post
    private String fetchJSONQuestions() throws IOException {
        URL url = new URL("https://opentdb.com/api.php?amount=1");
        HttpURLConnection con = (HttpURLConnection)url.openConnection();

        InputStreamReader isr = new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8.name());
        StringBuilder jsonStr = new StringBuilder();
        try (BufferedReader br = new BufferedReader(isr)) {
            String responseLine;

            while ((responseLine = br.readLine()) != null) {
                jsonStr.append(responseLine.trim());
            }
        }

        return jsonStr.toString();
    }

    //extracting question and answer from json
    private String[] extractJSONQuestion() throws IOException {
        String jsonStr = fetchJSONQuestions();

        return new String[] {
            cleanJSON(jsonStr, "question", "correct_answer"),
            cleanJSON(jsonStr, "correct_answer", "incorrect_answers"),
            cleanJSON(jsonStr, "difficulty", "question"), //TODO: option to choose difficulty
            cleanJSON(jsonStr, "incorrect_answers", "]"),
        };
    }
}