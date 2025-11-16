package ru.batorfly;

import java.util.Scanner;

public class IOUtils {

    public static int scanIntTableSize() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the number of table size in the range [1; 32]: ");
        int value;

        while (true) {
            String userInput = sc.nextLine();
            try {
                value = Integer.parseInt(userInput);
                if (value >= 1 && value <= 32) {
                    break;
                } else {
                    System.out.println("Invalid input. Enter an integer in the range [1; 32]:");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Enter an integer in the range [1; 32]: ");
            }
        }
        return value;
    }

    public static void print(String line){
        System.out.println(line);
    }

}
