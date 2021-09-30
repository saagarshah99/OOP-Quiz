package com._nology;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

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

    public int getNumberOfQuestions() {
        return numberOfQuestions;
    }

    //fetching questions from api and storing them in array
    private String[][] generateQuestions() throws IOException {
        String[][] questions = new String[this.numberOfQuestions][3];

        for (int i = 0; i < questions.length; i++) {
            String[] newQuestion = extractJSONQuestion();

            questions[i][0] = newQuestion[0]; //question
            questions[i][1] = newQuestion[1]; //answer
            questions[i][2] = newQuestion[2]; //difficulty
        }

        return questions;
    }

    //removing unwanted characters from json data extracted from api
    public static String cleanString(String str, String jsonKey) {
        String[] sectionsToRemove = {"\"", ",", jsonKey};

        for (int i = 0; i < sectionsToRemove.length; i++) {
            str = str.replace(sectionsToRemove[i], "");
        }

        str = str.replace("&quot;", "'");
        return str;
    }
    public String cleanJSON(String jsonStr, String key1, String key2) {
        return cleanString(
            jsonStr.substring(
                jsonStr.indexOf(key1),
                jsonStr.indexOf(key2)
            ), key1 + ":"
        );
    }

    //fetching questions from api https://www.baeldung.com/httpurlconnection-post
    public String fetchJSONQuestions() throws IOException {
        URL url = new URL("https://opentdb.com/api.php?amount=1");
        HttpURLConnection con = (HttpURLConnection)url.openConnection();

        StringBuilder response = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
            String responseLine = null;

            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
        }

        return response.toString();
    }

    //extracting question and answer from json
    public String[] extractJSONQuestion() throws IOException {
        String jsonStr = fetchJSONQuestions();

        return new String[] {
            cleanJSON(jsonStr, "question", "correct_answer"),
            cleanJSON(jsonStr, "correct_answer", "incorrect_answers"),
            cleanJSON(jsonStr, "difficulty", "question"), //TODO: option to choose difficulty
        };
    }
}