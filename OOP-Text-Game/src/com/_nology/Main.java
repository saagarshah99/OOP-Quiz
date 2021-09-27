package com._nology;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
            String answer = questions[i][1];

            String inputAns = Utils.inputBox(questionNumber + question);
            checkAnswer(inputAns, answer, player);
        }

        Scoreboard scores = new Scoreboard();
        scores.updateScoreboardFile(player);
        scores.printScoreboard(player);
    }

    //determine correctness of current question and update/return score (add points if corrects)
    public static void checkAnswer(String inputAns, String answer, Player player) {
        if(inputAns.equalsIgnoreCase(answer)) {
            int newPoints = Utils.randomNumber(1, 5);
            player.setScore(newPoints);

            generateSuccessMsg(newPoints, player.getScoreStr());
        }
        else generateFailMsg(player.getScoreStr(), answer);
    }

    //output "random" success message if given answer was correct (include score)
    public static void generateSuccessMsg(int newPoints, String scoreStr) {
        String[] possibleMessages = {"Correct!", "Awesome!", "Sound as a pound!"};
        String chosenMsg = possibleMessages[Utils.randomNumber(0, possibleMessages.length)];

        String points = newPoints != 1 ? " points" : " point";
        Utils.messageBox(chosenMsg + " (+" + newPoints + points + ") \n" + scoreStr);
    }

    //output message if given answer was incorrect (include score)
    public static void generateFailMsg(String scoreStr, String answer) {
        Utils.messageBox("Incorrect! The correct answer was '" + answer + "'\n" + scoreStr);
    }
}