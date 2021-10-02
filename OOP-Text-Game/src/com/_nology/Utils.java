package com._nology;

import java.util.Arrays;
import java.util.Objects;
import java.util.Random;
import javax.swing.*;

public class Utils {
    public static int randomNumber(int min, int max) {
        return new Random().nextInt(max) + min;
    }

    //output alert box with given message
    public static void messageBox(String str) {
        JOptionPane.showMessageDialog(null, str);
    }

    //output input box prompting user to enter some data
    //TODO: add validation to reject empty strings
    public static String inputBox(String promptMsg) {
        return JOptionPane.showInputDialog(promptMsg);
    }

    //remove null items from a string array
    public static String[] removeNullStrArrItems(String[] arr) {
        return Arrays.stream(arr).filter(Objects::nonNull).toArray(String[]::new);
    }

    //output input box prompting user to select one of the given options in response to a question
    public static String inputChoice(String promptMsg, String[] choices) {
        String[] choicesCleaned = removeNullStrArrItems(choices);

        return (String) JOptionPane.showInputDialog(
            null, promptMsg,
            "Please answer...",
            JOptionPane.QUESTION_MESSAGE, null,
            choicesCleaned,
            choicesCleaned[0] //select first option by default
        );
    }

    //return a line break string for breaking up output
    public static String lineBreak() {
        return "\n*****************************************************\n";
    }
}