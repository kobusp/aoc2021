package com.kobus.aoc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day3 extends AoCRunnable {

    public static void main(String[] args) throws IOException {
        (new AoC()).run(new Day3(),
                "3",
                true);

        (new AoC()).run(new Day3(),
                "3",
                false);
    }

    @Override
    public String part1() {
        int answer = 0;

        int gamma = 0;
        int epsilon = 0;

        String gammaStr = "";
        String epsilonStr = "";

        int length = input.get(0).length();

        for (int i = 0; i < length; i++) {
            int num0 = 0;
            int num1 = 0;
            for (var line : input) {
                if (line.charAt(i) == '0') {
                    num0++;
                } else {
                    num1++;
                }
            }
            if (num0 > num1) {
                gammaStr = gammaStr + "0";
                epsilonStr = epsilonStr + "1";
            } else {
                gammaStr = gammaStr + "1";
                epsilonStr = epsilonStr + "0";
            }
        }

        gamma = Integer.parseInt(gammaStr, 2);
        epsilon = Integer.parseInt(epsilonStr, 2);

        answer = gamma * epsilon;

        return "" + answer;
    }

    @Override
    public String part2() {
        int answer = 0;

        String oxygenRating = findMostCommon(input, 0);
        String co2Rating = findLeastCommon(input, 0);

        int oxygen = Integer.parseInt(oxygenRating, 2);
        int co2 = Integer.parseInt(co2Rating, 2);

        answer = oxygen * co2;

        return "" + answer;
    }

    private String findMostCommon(List<String> input, int i) {
        if (input.size() == 1) {
            return input.get(0);
        } else {
            var filtered = new ArrayList<String>();
            int num0 = 0;
            int num1 = 0;
            for (var line : input) {
                if (line.charAt(i) == '0') {
                    num0++;
                } else {
                    num1++;
                }
            }

            var mostCommon = num0 > num1 ? "0" : "1";
            for (var line : input) {
                if (line.substring(i, i + 1).equals(mostCommon)) {
                    filtered.add(line);
                }
            }

            return findMostCommon(filtered, i + 1);
        }
    }

    private String findLeastCommon(List<String> input, int i) {
        if (input.size() == 1) {
            return input.get(0);
        } else {
            var filtered = new ArrayList<String>();
            int num0 = 0;
            int num1 = 0;
            for (var line : input) {
                if (line.charAt(i) == '0') {
                    num0++;
                } else {
                    num1++;
                }
            }

            var leastCommon = num0 <= num1 ? "0" : "1";
            for (var line : input) {
                if (line.substring(i, i + 1).equals(leastCommon)) {
                    filtered.add(line);
                }
            }

            return findLeastCommon(filtered, i + 1);
        }
    }
}