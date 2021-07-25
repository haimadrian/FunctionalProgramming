package il.ac.hit.functionalprogramming.haim.cw3.java;

import java.util.Scanner;

/**
 * @author Haim Adrian
 * @since 25 Jul 2021
 */
public class SumOfFactorials {
    private static long factorial(int num) {
        if (num <= 1) {
            return 1;
        }

        return num * factorial(num - 1);
    }

    private static long factorialsInBetween(int num1, int num2) {
        int maxNumber = Math.max(num1, num2);
        int minNumber = Math.min(num1, num2);

        // Keep a reference to last calculated factorial, so avoid of repeating calculation over and over
        // of factorials we have already calculated.
        // For example, 3! + 4! + 5! is: 3! + 4*3! + 5*4!
        long lastFactorial = factorial(minNumber);
        long sumOfFactorials = lastFactorial;

        for (int num = minNumber + 1; num <= maxNumber; num++) {
            lastFactorial *= num;
            sumOfFactorials += lastFactorial;
        }

        return sumOfFactorials;
    }

    public static void main(String[] args) {
        int num1, num2;

        System.out.println("Please enter two natural numbers:");
        try (Scanner scanner = new Scanner(System.in)) {
            num1 = readIntSafe(scanner, 0, 50);
            num2 = readIntSafe(scanner, 0, 50);
        }

        System.out.println("Sum of factorials between " + num1 + " to " + num2 + " is: " + factorialsInBetween(num1, num2));
    }

    private static int readIntSafe(Scanner scanner, int minValue, int maxValue) {
        int choice = minValue - 1;
        do {
            String input = scanner.nextLine().trim();
            try { choice = Integer.parseInt(input); } catch (Exception ignore) { }

            if ((choice < minValue) || (choice > maxValue)) {
                System.err.println("Wrong input. Range is: [" + minValue + ", " + maxValue + "]. Please try again:");
            }
        } while ((choice < minValue) || (choice > maxValue));

        return choice;
    }
}
