package com.codsoft;

import java.util.Random;
import java.util.Scanner;

public class NumberGuessingGame {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        int minRange = 1;
        int maxRange = 100;
        int maxAttempts = 10;
        int rounds = 0;
        int score = 0;

        System.out.println("Welcome to the Number Guessing Game!");

        do {
            int generatedNumber = random.nextInt(maxRange - minRange + 1) + minRange;
            int attempts = 0;
            rounds++;

            System.out.println("Round " + rounds + ": Guess a number between " + minRange + " and " + maxRange + ".");

            while (attempts < maxAttempts) {
            	System.out.println("Attempts left: " + (maxAttempts - attempts));
                System.out.print("Enter your guess: ");
                int userGuess = scanner.nextInt();
                attempts++;

                if (userGuess == generatedNumber) {
                    System.out.println("Congratulations! Your guess is correct!");
                    score += maxAttempts - attempts + 1;
                    break;
                } else if (userGuess < generatedNumber) {
                    System.out.println("Too low! Try again.");
                } else {
                    System.out.println("Too high! Try again.");
                }
            }

            if (attempts >= maxAttempts) {
                System.out.println("Out of attempts. The number was: " + generatedNumber);
            }

            System.out.print("Do you want to play again? (yes/no): ");
            String playAgain = scanner.next();
            if (playAgain.equalsIgnoreCase("no")) {
                break;
            }

        } while (true);

        System.out.println("Game Over!");
        System.out.println("Total rounds played: " + rounds);
        System.out.println("Your score: " + score);
        scanner.close();
    }
}
