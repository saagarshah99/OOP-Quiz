package com._nology;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        setupQuiz();
    }

    //instantiating new quiz and player, receive name as user input and starting game
    public static void setupQuiz() throws IOException {
        String name = Utils.inputBox("Enter your name...");
        Player player = new Player(name);

        printWelcomeMsg(player.getName());
        askQuestions(player);
    }

    //output welcome message after user enters their name using message box
    public static void printWelcomeMsg(String name) {
        Utils.messageBox(
            "Welcome " + name +
            "! Answer the following questions..."
        );
    }

    //loop through to ask questions, receive answers and output score throughout
    public static void askQuestions(Player player) throws IOException {
        String[][] questions = player.getQuestions();

        for (int i=0; i<questions.length; i++) {
            String questionNumber = "\n" + (i + 1) + ") ";
            String inputAns = Utils.inputBox(questionNumber + questions[i][0]);

            checkAnswer(inputAns, questions[i][1], player);
        }

        updateScoreboard(player);
        printScoreboard(player);
    }

    //determine correctness of current question and update/return score (add points if corrects)
    //TODO: make this multiple choice
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

        Utils.messageBox(
            chosenMsg + " (+" + newPoints + " points) \n" +
            scoreStr
        );
    }

    //output message if given answer was incorrect (include score)
    public static void generateFailMsg(String scoreStr, String answer) {
        Utils.messageBox(
            "Incorrect! The correct answer was '" + answer +
            "'\n" + scoreStr
        );
    }

    //fetch updated scoreboard from file, loop through it and output it appropriately (end of quiz)
    public static void printScoreboard(Player player) throws IOException {
        String output = "\nSCOREBOARD:\n";

        List<List<String>> scoreboard = getScoreboard(); // [name, score]
        for (int i = 0; i < scoreboard.size(); i++)
        {
            String rowOutput = "";
            for (int j = 0; j < scoreboard.get(i).size(); j++)
            {
                String currentValue = scoreboard.get(i).get(j);

                if(j == 0) {
                    rowOutput = currentValue + ": ";

                    //make recent score stand out
                    if(i == scoreboard.size()-1) {
                        rowOutput = "\n" + currentValue + " (You): ";
                    }
                }
                else rowOutput += currentValue + " points";
            }

            output += rowOutput + "\n";
        }

        Utils.messageBox(
                "\nEnd of questions " + player.getName() + "...\n" +
                Utils.lineBreak() + output
        );
    }

    //loading existing and new player score data into csv file
    public static void updateScoreboard(Player player) throws IOException {
        List<List<String>> scoreboard = getScoreboard();

        FileWriter csvWriter = new FileWriter("scoreboard.csv");

        //existing scores
        for (List<String> row : scoreboard) {
            csvWriter.append(String.join(",", row));
            csvWriter.append("\n");
        }

        //new scores
        csvWriter.append(player.getName()).append(", ").append(String.valueOf(player.getScore()));

        csvWriter.flush();
        csvWriter.close();
    }

    //reading player scores from a potentially existing csv file and storing in arraylist
    public static List<List<String>> getScoreboard() throws IOException {
        List<List<String>> scoreboardList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("scoreboard.csv"))) {
            String row;
            while ((row = br.readLine()) != null) {
                scoreboardList.add(Arrays.asList(row.split(",")));
            }
        }

        return scoreboardList;
    }
}