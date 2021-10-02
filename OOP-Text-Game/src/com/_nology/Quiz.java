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
        String[][] questions = new String[this.numberOfQuestions][3];

        for (int i = 0; i < questions.length; i++) {
            String[] newQuestion = extractJSONQuestion();

            questions[i][0] = newQuestion[0]; //question
            questions[i][1] = newQuestion[1]; //answer
            questions[i][2] = newQuestion[2]; //difficulty
        }

        //ensuring I can answer one correctly for demo purposes
        questions[questions.length-1][0] = "What does CLI typically stand for in computing?";
        questions[questions.length-1][1] = "Command Line Interface";

        return questions;
    }

    //removing unwanted characters from json question data extracted from api
    public String cleanJSON(String jsonStr, String key1, String key2) {
        String sanitisedStr = jsonStr.substring(jsonStr.indexOf(key1), jsonStr.indexOf(key2));

        for(String section : new String[] {"\"", ",", key1 + ":"}) {
            sanitisedStr = sanitisedStr.replace(section, "");
        }

        sanitisedStr = sanitisedStr.replace("&quot;", "'"); //quotations

        return sanitisedStr;
    }

    //fetching questions from api https://www.baeldung.com/httpurlconnection-post
    public String fetchJSONQuestions() throws IOException {
        URL url = new URL("https://opentdb.com/api.php?amount=1");
        HttpURLConnection con = (HttpURLConnection)url.openConnection();

        InputStreamReader isr = new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8.name());
        StringBuilder response = new StringBuilder();
        try (BufferedReader br = new BufferedReader(isr)) {
            String responseLine;

            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
        }

        return response.toString();
    }

    //extracting question and answer from json
    public String[] extractJSONQuestion() throws IOException {
        String jsonStr = fetchJSONQuestions();

        //rejecting multiple choice/boolean questions for now
        String questionType = cleanJSON(jsonStr, "type", "difficulty");
        if(!questionType.equals("multiple") || !questionType.equals("boolean")) {
            return new String[] {
                cleanJSON(jsonStr, "question", "correct_answer"),
                cleanJSON(jsonStr, "correct_answer", "incorrect_answers"),
                cleanJSON(jsonStr, "difficulty", "question"), //TODO: option to choose difficulty
            };
        }
        else {
            Utils.messageBox("wrong question type: " + questionType);
            return extractJSONQuestion();
        }
    }
}


/*
    Format of JSON Question:
    {
        "response_code":0,
        "results": [
            {
                "category": "History",
                "type": "multiple",
                "difficulty": "medium",
                "question": "What automatic assault rifle was developed in the Soviet Union in 1947?",
                "correct_answer":"AK-47",
                "incorrect_answers":["RPK","M16","MG 42"]
            }
        ]
    }
*/