package com._nology;

import javax.swing.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        setupQuiz();
    }

    //instantiating new player, receiving name as input, outputting welcome msg and starting game
    public static void setupQuiz() throws IOException {
        String name = Utils.inputBox("Enter your name...");
        int numberOfQuestions = Integer.parseInt(Utils.inputBox("Welcome " + name + "! How many questions?"));

        String pleaseWaitMsg = "Randomly selecting questions, please wait...";
        System.out.println(pleaseWaitMsg);
        Utils.messageBox(pleaseWaitMsg);

        Player player = new Player(numberOfQuestions, name);
        askQuestions(player);
    }

    //loop through to ask multiple choice questions, receive answers and output score throughout
    public static void askQuestions(Player player) throws IOException {
        String[][] questions = player.getQuestions();

        for (int i = 0; i < questions.length; i++) {
            String question = "\n" + (i + 1) + ") " + questions[i][0];

            String correctAnswer = questions[i][1].trim();
            String[] incorrectAnswers = questions[i][3].split(",");

            //placing all possible answers in array, random position for correct one
            String[] choices = new String[incorrectAnswers.length + 1];

            if(correctAnswer.equalsIgnoreCase("true") || correctAnswer.equalsIgnoreCase("false")) {
                choices[0] = "True";
                choices[1] = "False";
            }
            else {
                int randomIndex = Utils.randomNumber(0, choices.length - 1);
                for(int j = 0; j < choices.length - 1; j++) {
                    choices[j] = (j == randomIndex) ? correctAnswer : incorrectAnswers[j].trim();
                }
            }

            String inputAns = (String) JOptionPane.showInputDialog(
                null,
                question,
                "Please answer...",
                JOptionPane.QUESTION_MESSAGE, null,
                choices,
                choices[0] //select question by default
            );

            checkAnswer(inputAns, correctAnswer, player);
        }

        new Scoreboard().updateScoreboardFile(player);
    }

    //determine correctness of current question, generate random msg and update/return score
    public static void checkAnswer(String inputAns, String correctAnswer, Player player) {
        if (inputAns.equalsIgnoreCase(correctAnswer)) {
            int newScore = Utils.randomNumber(1, 5);
            player.setScore(newScore);
            Utils.messageBox(new SuccessMessage(player, newScore).getResponse());
        }
        else {
            Utils.messageBox(new FailMessage(correctAnswer, player.getScoreStr()).getResponse());
        }
    }
}