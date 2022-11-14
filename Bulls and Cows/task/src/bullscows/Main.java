package bullscows;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

import static java.util.Arrays.sort;


public class Main {

    static Scanner scanner = new Scanner(System.in);
    public static String code1;

    public static void main(String[] args) {
        System.out.println("Input the length of the secret code:");
        int digits = 0;
        try {
            digits = scanner.nextInt();
        } catch (Exception e) {
            System.out.println("error");
            System.exit(0);
        }
        System.out.println("Input the number of possible symbols in the code:");
        int symbols = scanner.nextInt()-10;
        scanner.nextLine();
        if (digits-symbols > 10 || digits <= 0 || symbols > 26) {
            System.out.printf("Error: can't generate a code with a length of %d with %d unique symbols.", digits, symbols);
        } else {
            code1 = randomNumberGenerator(digits, symbols);
            game(symbols);
        }
    }
    public static void game(int symbols){
        StringBuilder codeStars = new StringBuilder("");
        StringBuilder usedSymbols = new StringBuilder("");
        for (int i = 0; i < code1.length(); i++) {
            codeStars.append("*");
        }
        usedSymbols.append(Character.toString((char) symbols+96));
        System.out.printf("The secret is prepared: %s (0-9, a-%s). %s%n", codeStars, usedSymbols, code1);
        System.out.println("Okay, let's start a game!");
        int turnCounter = 1;
        String[] _code = code1.split("");
        do {
            System.out.printf("%nTurn %d:%n", turnCounter);
            String guess = scanner.nextLine();
            guessTest(guess, _code);
            turnCounter++;
        } while(true);
    }
    public static String randomNumberGenerator(int digits, int symbols) {
        Random random = new Random();
        do {
            StringBuilder randomDigits = new StringBuilder();
            int symbolCount = symbols;
            for (int i = 0; i < digits; i++) {
                if (symbolCount > 0) {
                    if (random.nextInt(2) > 0) {
                        randomDigits.append(Character.toString((char) (random.nextInt(symbols) + 97)));
                        symbolCount--;
                    } else {
                        randomDigits.append(Integer.toString(random.nextInt(10)));
                    }
                } else {
                    randomDigits.append(Integer.toString(random.nextInt(10)));
                }
            }
            char[] isUniqueTest = new char[randomDigits.length()];
            randomDigits.getChars(0, randomDigits.length(), isUniqueTest, 0);
            sort(isUniqueTest);
            //System.out.println("Test " + randomDigits);
            //System.out.println("Test " + Arrays.toString(isUniqueTest));
            if (isUniqueTest.length < 2) {
                return randomDigits.toString();
            }
            for (int i = 1; i < isUniqueTest.length; i++) {
                if (isUniqueTest[i] == isUniqueTest[i-1]) {
                    break;
                } else if (i == isUniqueTest.length-1) {
                    return randomDigits.toString();
                }
            }
        } while (true);
    }
    public static void guessTest(String guess, String[] code) {
        int cow = 0;
        int bull = 0;
        String[] parts = guess.split("");
        for (int i = 0; i < parts.length; i++) {
            for (int k = 0; k < code.length; k++) {
                if (k != i) {
                    if (code[k].equals(parts[i])) {
                        cow++;
                    }
                } else {
                    if (code[k].equals(parts[i])) {
                        bull++;
                    }
                }
            }
        }
        StringBuilder bullStr = new StringBuilder("bull");
        StringBuilder cowStr = new StringBuilder("cow");
        if (bull > 1) {
            bullStr.append("s");
        }
        if (cow > 1) {
            cowStr.append("s");
        }
        if (bull == String.valueOf(code1).length()) {
            System.out.printf("Grade: %d %s%n", bull, bullStr);
            System.out.println("Congratulations! You guessed the secret code.");
            System.exit(0);
        } else if (bull > 0 && cow > 0) {
            System.out.printf("Grade: %d %s and %d %s", bull, bullStr, cow, cowStr);
        } else if (bull > 0) {
            System.out.printf("Grade: %d %s", bull, bullStr);
        } else if (cow > 0) {
            System.out.printf("Grade: %d %s", cow, cowStr);
        } else {
            System.out.println("None");
        }
    }
}
