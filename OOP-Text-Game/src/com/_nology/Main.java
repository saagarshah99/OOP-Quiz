package com._nology;

import netscape.javascript.JSObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class Main {
    public static void main(String[] args) throws IOException {
        setupQuiz();
    }

    //instantiating new player, receiving name as input, outputting welcome msg and starting game
    public static void setupQuiz() throws IOException {
        String name = Utils.inputBox("Enter your name...");
        int numberOfQuestions = Integer.valueOf(Utils.inputBox("Welcome " + name + "! How many questions?"));

        Player player = new Player(numberOfQuestions, name);
        askQuestions(player);
    }

    //loop through to ask questions, receive answers and output score throughout
    public static void askQuestions(Player player) throws IOException {
        String[][] questions = player.getQuestions();

        for (int i=0; i<questions.length; i++) {
            String question = "\n" + (i + 1) + ") " + questions[i][0];
            String inputAns = Utils.inputBox(question);
            String correctAnswer = questions[i][1];

            checkAnswer(inputAns, correctAnswer, player);
        }

        new Scoreboard(0).updateScoreboardFile(player);
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