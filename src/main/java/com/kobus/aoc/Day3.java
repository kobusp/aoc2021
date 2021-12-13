package com.kobus.aoc;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Advent of Code 2021 Solutions
 * Day 3: Binary Diagnostic
 *
 * @author Kobus Pretorius
 */
public class Day3 extends AoCRunnable {

    public static void main(String[] args) throws IOException {
        new Day3("3").run(true);
        new Day3("3").run(false);
    }

    public Day3(String dayNumber) {
        super(dayNumber);
    }

    @Override
    public String part1() {
        StringBuilder gammaStr = new StringBuilder();

        int length = input.get(0).length();

        for (int i = 0; i < length; i++) {
            int finalI = i;
            int num0 = input.stream().mapToInt(l -> l.charAt(finalI) == '0' ? 1 : 0).sum();
            int num1 = input.size() - num0;

            gammaStr.append(num0 > num1 ? "0" : "1");
        }

        int gamma = parseBinaryStr(gammaStr.toString());
        int mask = (1 << length) - 1;
        int epsilon = ~gamma & mask; // Epsilon is the bitwise NOT of gamma

        return "" + gamma * epsilon;
    }

    @Override
    public String part2() {
        int oxygen = parseBinaryStr(findCommon(input, 0, true));
        int co2 = parseBinaryStr(findCommon(input, 0, false));

        return "" + oxygen * co2;
    }

    // Recursive method until only one line of input remains
    private String findCommon(List<String> input, final int i, boolean mostCommon) {
        if (input.size() == 1) {
            return input.get(0);
        } else {
            int num0 = input.stream().mapToInt(l -> l.charAt(i) == '0' ? 1 : 0).sum();
            int num1 = input.size() - num0;

            var matchChar = mostCommon ? num0 > num1 ? "0" : "1" : num0 <= num1 ? "0" : "1";

            var filtered = input.stream()
                    .filter(l -> l.substring(i, i + 1).equals(matchChar))
                    .collect(Collectors.toList());

            return findCommon(filtered, i + 1, mostCommon);
        }
    }
}