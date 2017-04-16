package com.nstosic.codejam.tidynumbers;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Nemanja on 4/8/2017.
 */
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int testCases = scanner.nextInt();
        long testInput, testOutput;
        for(int i = 1; i <= testCases; i++) {
            testInput = readInput(scanner);
            testOutput = solveTestCase(testInput);
            System.out.println(String.format("Case #%d: %d", i, testOutput));
        }
    }

    private static long readInput(Scanner scanner) {
        return scanner.nextLong();
    }

    private static long solveTestCase(long testInput) {
        if(isTidy(testInput)) {
            return testInput;
        }
        return smallerTidyNumber(testInput);
    }

    private static boolean isTidy(long number) {
        if (number < 10) {
            return true;
        }
        int previousDigit = (int) (number % 10);
        int digit;
        while(number > 0) {
            number = number / 10;
            digit = (int) (number % 10);
            if (digit > previousDigit) {
                return false;
            }
            previousDigit = digit;
        }
        return true;
    }

    private static long smallerTidyNumber(long number) {
        LinkedList<Integer> buffer = new LinkedList<>();
        int previousDigit = (int) (number % 10);
        number = number / 10;
        int digit = 0, prefix;
        if (previousDigit > 0) {
            buffer.add(previousDigit);
            prefix = 0;
        }
        else {
            buffer.add(9);
            prefix = -1;
        }
        while(number > 0) {
            digit = prefix + (int) (number % 10);
            if(digit == 0) {
                buffer = bumpToNines(buffer);
                prefix = - 1;
                if (number >= 100) {
                    digit = 9;
                }
                else {
                    buffer.add(9);
                    number = number / 10;
                    digit = (int) (number % 10);
                    buffer.add(digit - 1);
                    return digitsToNumber(buffer);
                }
            }
            else {
                if (digit > previousDigit) {
                    if (prefix == 0) {
                        digit = digit - 1;
                    }
                    buffer = bumpToNines(buffer);
                }
                prefix = 0;
            }
            buffer.add(digit);
            previousDigit = digit;
            number = number / 10;
        }
        return digitsToNumber(buffer);
    }

    private static LinkedList<Integer> bumpToNines(LinkedList<Integer> list) {
        int size = list.size();
        list.clear();
        for(int i = 0; i < size; i++) {
            list.add(9);
        }
        return list;
    }

    private static long digitsToNumber(LinkedList<Integer> digits) {
        long number = 0;
        long base = 1;
        while(!digits.isEmpty()) {
            number = number + base * digits.pop();
            base *= 10;
        }
        return number;
    }
}
