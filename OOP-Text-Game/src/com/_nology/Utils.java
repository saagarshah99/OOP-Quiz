package com._nology;

import java.util.Random;
import javax.swing.*;

public class Utils {

    //generate random number within a given range
    public static int randomNumber(int min, int max) {
        return new Random().nextInt(max) + min;
    }

    //output string to console
    public static void print(String str) {
        System.out.println(str);
    }

    //output alert box with given message
    public static void messageBox(String str) {
        JOptionPane.showMessageDialog(null, str);
    }

    //output input box prompting user to enter some data
    //TODO: add validation to reject empty strings
    public static String inputBox(String str) {
        return JOptionPane.showInputDialog(str);
    }

    //return a line break string for breaking out console output
    public static String lineBreak() {
        return "\n*****************************************************\n";
    }
}