package com._nology;

import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        setupQuiz();
    }

    //instantiating new player, receiving name as input, outputting welcome msg and starting game
    public static void setupQuiz() throws IOException {
        Player player = new Player(Utils.inputBox("Enter your name...")); //extends Quiz

        Utils.messageBox("Welcome " + player.getName() + "! Answer the following questions...");

        askQuestions(player);
    }

    //loop through to ask questions, receive answers and output score throughout
    public static void askQuestions(Player player) throws IOException {
        String[][] questions = player.getQuestions();

        for (int i=0; i<questions.length; i++) {
            String questionNumber = "\n" + (i + 1) + ") ";
            String question = questions[i][0];
            String correctAnswer = questions[i][1];

            String inputAns = Utils.inputBox(questionNumber + question);
            checkAnswer(inputAns, correctAnswer, player);
        }

        Scoreboard scores = new Scoreboard();
        scores.updateScoreboardFile(player);
    }

    //determine correctness of current question, generate random msg and update/return score
    public static void checkAnswer(String inputAns, String correctAnswer, Player player) {
        if(inputAns.equalsIgnoreCase(correctAnswer)) {
            player.setScore(Utils.randomNumber(1, 5));
            Utils.messageBox(new SuccessMessage(player).getResponse());
        }
        else {
            Utils.messageBox(new FailMessage(correctAnswer, player.getScoreStr()).getResponse());
        }
    }
}