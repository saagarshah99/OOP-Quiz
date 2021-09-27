package com._nology;

public class Quiz {
    private final String[][] questions;

    public Quiz() {
        this.questions = this.generateQuestions();
    }

    public String[][] getQuestions() {
        return this.questions;
    }

    /*
        TODO: fetch questions from API later
            https://opentdb.com/api.php?amount=10
            https://medium.com/swlh/getting-json-data-from-a-restful-api-using-java-b327aafb3751

        TODO: use nested array to accept multiple possible answers

        TODO: could randomly generate array indexes and store them in an array to avoid duplicates
         in order to select from a larger set of questions
    */
    private String[][] generateQuestions() {
        return new String[][] {
            {
                "Nutella is produced by the German company Ferrero (True or False?)",
                "True"
            },
//            {
//                "In computing terms, typically what does CLI stand for?",
//                "Command Line Interface"
//            },
//            {"What is the capital of Chile?", "Santiago"},
//            {
//                "What is isobutylphenylpropanoic acid more commonly known as?",
//                "Ibuprofen"
//            },
//            {"What is the highest mountain in the world?", "Everest"},
//            {"How many people at _nology are left handed?", "2"}
        };
    }
}