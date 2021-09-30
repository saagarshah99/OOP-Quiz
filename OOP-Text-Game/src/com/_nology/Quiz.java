package com._nology;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Quiz {
    private final String[][] questions;
    private int numberOfQuestions;

    public Quiz(int numberOfQuestions) throws IOException {
        this.numberOfQuestions = numberOfQuestions;
        this.questions = this.generateQuestions();
    }

    public String[][] getQuestions() {
        return this.questions;
    }

    //fetching questions from api and storing them in array
    private String[][] generateQuestions() throws IOException {
        String[][] questions = new String[this.numberOfQuestions][2];

        for(int i=0; i<questions.length; i++) {
            String[] newQuestion = extractJSONQuestion();

            questions[i][0] = newQuestion[0];
            questions[i][1] = newQuestion[1];
        }

        return questions;
    }

    //removing unwanted characters from json data extracted from api
    public static String cleanString(String str, String jsonKey) {
        String[] sectionsToRemove = {"\"", ",", jsonKey};

        for(int i=0; i<sectionsToRemove.length; i++) {
            str = str.replace(sectionsToRemove[i], "");
        }

        str = str.replace("&quot;", "'");
        return str;
    }

    //fetching questions from api https://www.baeldung.com/httpurlconnection-post
    public String fetchJSONQuestions() throws IOException {
        URL url = new URL("https://opentdb.com/api.php?amount=1");
        HttpURLConnection con = (HttpURLConnection)url.openConnection();

        StringBuilder response = new StringBuilder();
        try(BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
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
            cleanString(
                jsonStr.substring(
                    jsonStr.indexOf("question"), jsonStr.indexOf("correct_answer")
                ), "question:"),

            cleanString(
                jsonStr.substring(
                    jsonStr.indexOf("correct_answer"), jsonStr.indexOf("incorrect_answers")
                ), "correct_answer:"
            )
        };
    }
}
