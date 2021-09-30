package com._nology;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Scoreboard extends Quiz {
    private List<List<String>> scoreboardList = new ArrayList<>();

    public Scoreboard(int numberOfQuestions) throws IOException {
        super(numberOfQuestions);
        this.scoreboardList = loadScoreboardFromFile();
    }



    /***********************FETCHING/UPDATING SCORES*************************/

    public List<List<String>> getScoreboardList() {
        return this.scoreboardList;
    }

    public void setScoreboardList() throws IOException {
        this.scoreboardList = loadScoreboardFromFile();
    }

    //reading player scores from a potentially existing csv file and storing in arraylist
    public List<List<String>> loadScoreboardFromFile() throws IOException {
        List<List<String>> scoreboardList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("scoreboard.csv"))) {
            String row;
            while ((row = br.readLine()) != null) {
                scoreboardList.add(Arrays.asList(row.split(",")));
            }
        }

        return scoreboardList;
    }

    //loading existing and new player score data into csv file
    public void updateScoreboardFile(Player player) throws IOException {
        List<List<String>> scoreboard = getScoreboardList();

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

        setScoreboardList();
        printScoreboard(player);
    }



    /*************************OUTPUTTING SCORES*************************/

    //fetch updated scoreboard from file, loop through it and output it appropriately (end of quiz)
    public void printScoreboard(Player player) throws IOException {
        List<List<String>> scoreboard = getScoreboardList(); // [name, score]

        String output = "\nSCOREBOARD:\n";
        for (int i = 0; i < scoreboard.size(); i++)
        {
            String currentRow = "";

            for (int j = 0; j < scoreboard.get(i).size(); j++)
            {
                currentRow += printScoreboardRow(i, j);
            }

            output += currentRow + "\n";
        }

        Utils.messageBox(
            "\nEnd of questions " + player.getName() + "...\n" +
            Utils.lineBreak() + output
        );
    }

    public String printScoreboardRow(int i, int j) throws IOException {
        List<List<String>> scoreboard = getScoreboardList();

        String currentRow = "";
        String currentValue = scoreboard.get(i).get(j);
        if(j == 0) {
            currentRow = currentValue + ": ";

            //make recent score stand out
            if(i == scoreboard.size()-1) {
                currentRow = "\n" + currentValue + " (You): ";
            }
        }
        else {
            currentRow += currentValue + (currentValue.equals(" 1") ? " point" : " points");
        }

        return currentRow;
    }
}