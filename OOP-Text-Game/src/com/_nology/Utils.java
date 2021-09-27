package com._nology;

import java.util.Random;
import java.util.Scanner;
import javax.swing.*;

public class Utils {

    //generate random number within a given range
    public static int randomNumber(int min, int max) {
        return new Random().nextInt(max) + min;
    }

    //prompt user for input and return response as string
    public static String inputString(String promptMsg) {
        Scanner input = new Scanner(System.in);
        print(promptMsg);
        return input.nextLine();
    }

    //output string to console
    public static void print(String str) {
        System.out.println(str);
    }

    //output alert box with given message
    public static void messageBox(String str) {
        JOptionPane.showMessageDialog(null, str);
    }

    //return a line break string for breaking out console output
    public static String lineBreak() {
        return "\n*****************************************************\n";
    }
}
