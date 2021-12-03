package com.kobus.aoc;

import java.io.IOException;

/**
 * Advent of Code 2021 Solutions
 * Day 1
 *
 * @author Kobus Pretorius
 */
public class Day1 extends AoCRunnable {

    public static void main(String[] args) throws IOException {
        new Day1("1").run(true);
        new Day1("1").run(false);
    }

    public Day1(String dayNumber) {
        super(dayNumber);
    }

    @Override
    public String part1() {
        int answer = 0;
        int prev = -1;
        parseInputAsInt();
        for (var num1 : inputAsInt) {
            if (prev > -1 && num1 > prev) {
                answer++;
            }
            prev = num1;
        }
        return "" + answer;
    }

    @Override
    public String part2() {
        int answer = 0;
        parseInputAsInt();
        for (int i = 1; i < inputAsInt.size() - 2; i++) {
            int prevSum = inputAsInt.get(i - 1) + inputAsInt.get(i) + inputAsInt.get(i + 1);
            int sum = inputAsInt.get(i) + inputAsInt.get(i + 1) + inputAsInt.get(i + 2);
            if (sum > prevSum) {
                answer++;
            }
        }
        return "" + answer;
    }
}
