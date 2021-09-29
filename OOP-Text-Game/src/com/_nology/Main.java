package com._nology;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

public class Main {
    public static void main(String[] args) throws IOException {
//        URLConnection connection = new URL("https://opentdb.com/api.php?amount=10").openConnection();
//        connection.setRequestProperty("type", "header1");
//        connection.setRequestProperty("header2", "header2");
//
//        //Get Response
//        InputStream is = connection.getInputStream();
//        System.out.println(connection.type);
//        System.out.println(connection.getContentType());

        setupQuiz();
    }

    //instantiating new player, receiving name as input, outputting welcome msg and starting game
    public static void setupQuiz() throws IOException {
        Player player = new Player(Utils.inputBox("Enter your name..."));
        Utils.messageBox("Welcome " + player.getName() + "! Answer the following questions...");
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

        new Scoreboard().updateScoreboardFile(player);
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