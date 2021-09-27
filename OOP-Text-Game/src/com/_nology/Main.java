package com._nology;


import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    //ANSI text colour escape codes
    public static String ANSI_RESET = "\u001B[0m";

    public static void main(String[] args) throws IOException {
        setupQuiz();
    }

    //instantiating new quiz and player, receive name as user input and starting game
    public static void setupQuiz() throws IOException {
        String name = Utils.inputString("Enter your name...");
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

    //loop through to ask questions (light blue), receive answers and output score throughout
    public static void askQuestions(Player player) throws IOException {
        String[][] questions = player.getQuestions();

        for (int i=0; i<questions.length; i++) {
            String questionNumber = "\n" + (i + 1) + ") ";

            String inputAns = Utils.inputString(
                    "\u001B[36m" + questionNumber + questions[i][0] + ANSI_RESET
            );

            checkAnswer(inputAns, questions[i][1], player);
        }

        updateScoreboard(player);
        printFinalScore(player);
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

    //output "random" green success message if given answer was correct (include score)
    public static void generateSuccessMsg(int newPoints, String scoreStr) {
        String[] possibleMessages = {"Correct!", "Awesome!", "Sound as a pound!"};
        String chosenMsg = possibleMessages[Utils.randomNumber(0, possibleMessages.length)];

        Utils.print(
            "\u001B[32m" +
            chosenMsg + " (+" + newPoints + " points) \n" + ANSI_RESET +
            scoreStr
        );
    }

    //output red message if given answer was incorrect (include score)
    public static void generateFailMsg(String scoreStr, String answer) {
        Utils.print(
            "\u001B[31m" +
            "Incorrect! The correct answer was '" + answer + ANSI_RESET +
            "'\n" + scoreStr
        );
    }

    //output final score at end of quiz
    public static void printFinalScore(Player player) throws IOException {
        Utils.print(
            Utils.lineBreak() +
            "\nEnd of questions " + player.getName() + "..." +
            "\nFinal " + player.getScoreStr()
        );

        printScoreboard();
    }

    //fetch updated scoreboard from file, loop through it and output it appropriately
    public static void printScoreboard() throws IOException {
        Utils.print("\u001B[33m" + "\nSCOREBOARD:" + ANSI_RESET);

        List<List<String>> scoreboard = getScoreboard();
        for (int i = 0; i < scoreboard.size(); i++)
        {
            String rowOutput = "";
            for (int j = 0; j < scoreboard.get(i).size(); j++)
            {
                String currentValue = scoreboard.get(i).get(j);

                if(j == 0) {
                    rowOutput = "Name: " + currentValue;

                    //make recent score stand out in purple
                    if(i == scoreboard.size()-1) {
                        rowOutput += " (" + "\u001B[35m" + "You" + ANSI_RESET + ")";
                    }
                }
                else rowOutput += " --> " + currentValue + " points";
            }

            Utils.print(rowOutput);
        }
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