package org.yogusoft.consoleUtils;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ConsoleInput {
    public static int getPositive_Integer() {
        Scanner leer = new Scanner(System.in);
        boolean error = true;
        int x = 0;

        do {
            try {
                x = leer.nextInt();

                if (x < 0) {
                    System.out.println("Error, you entered a negative integer");
                    System.out.println("Please try again: ");
                } else {
                    error = false;
                }
            } catch (InputMismatchException e) {
                System.out.println("Error, you haven't entered an integer");
                System.out.println("Please try again: ");
                leer.nextLine();
            }
        } while (error);

        return x;
    }

    public static String getString(String defaultValue) {
        Scanner leer = new Scanner(System.in);
        boolean err = true;
        String x = "";

        do {
            try {
                x = leer.nextLine();

                if (x.equals("")) {
                    if (defaultValue.isEmpty()) {
                        System.out.println("Error, you have entered an empty string");
                        System.out.println("Please try again: ");
                    } else return defaultValue;

                } else {
                    err = false;
                }
            } catch (InputMismatchException e) {
                System.out.println("Error, you haven't entered a string " + e);
                System.out.println("Please try again: ");
                leer.nextLine();
            }
        } while (err);

        return x;
    }

    public static boolean getBoolean() {
        Scanner read = new Scanner(System.in);
        boolean err = true;
        boolean x = false;

        do {
            try {
                x = read.nextBoolean();
                err = false;
            } catch (InputMismatchException e) {
                System.out.println("Error, you haven't entered a boolean value");
                System.out.println("Please try again: ");
                read.nextLine();
            }
        } while (err);

        return x;
    }

}
