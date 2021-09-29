package com._nology;

import java.util.Random;

public class Quiz {
    private final String[][] questions;

    public Quiz() {
        this.questions = shuffleQuestions(this.generateQuestions());
    }

    public String[][] getQuestions() {
        return this.questions;
    }

    /*
        TODO: fetch questions from API later
            https://opentdb.com/api.php?amount=10
            https://medium.com/swlh/getting-json-data-from-a-restful-api-using-java-b327aafb3751
    */
    private String[][] generateQuestions() {
        return new String[][] {
            {
                "Nutella is produced by the German company Ferrero (True or False?)",
                "True"
            },
            {
                "In computing terms, typically what does CLI stand for?",
                "Command Line Interface"
            },
            {"What is the capital of Chile?", "Santiago"},
            {
                "What is isobutylphenylpropanoic acid more commonly known as?",
                "Ibuprofen"
            },
            {"What is the highest mountain in the world?", "Everest"},
            {"How many people at _nology are left handed?", "2"}
        };
    }


    public String[][] shuffleQuestions(String[][] questions) {
        int index;
        String[] temp;

        //reverse order, generating random index to move questions around
        for (int i = questions.length - 1; i > 0; i--) {
            index = Utils.randomNumber(0, i);
            temp = questions[index];
            questions[index] = questions[i];
            questions[i] = temp;
        }

        return questions;
    }
}
