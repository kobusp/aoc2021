package com.kobus.aoc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Advent of Code 2021 Solutions
 * Day 10: Syntax Scoring
 *
 * @author Kobus Pretorius
 */
public class Day10 extends AoCRunnable {

    public static void main(String[] args) throws IOException {
        new Day10("10").run(true);
        new Day10("10").run(false);
    }

    private String openChars = "([{<";
    private HashMap<String, Integer> scores = new HashMap<>();
    private HashMap<String, Long> scores2 = new HashMap<>();
    private HashMap<String, String> closing = new HashMap<>();
    private HashMap<String, String> opening = new HashMap<>();
    private List<String> validLines;

    public Day10(String dayNumber) {
        super(dayNumber);
        debugging = true;

        scores.put(")", 3);
        scores.put("]", 57);
        scores.put("}", 1197);
        scores.put(">", 25137);

        scores2.put(")", 1L);
        scores2.put("]", 2L);
        scores2.put("}", 3L);
        scores2.put(">", 4L);

        closing.put("(", ")");
        closing.put("[", "]");
        closing.put("{", "}");
        closing.put("<", ">");

        opening.put(")", "(");
        opening.put("]", "[");
        opening.put("}", "{");
        opening.put(">", "<");

    }

    @Override
    public String part1() {
        validLines = new ArrayList<>();
        int sum = 0;
        for (var line : input) {
            var score = getScore(line);
            if (score == 0) {
                validLines.add(line);
            }
            sum += score;
        }
        return "" + sum;
    }


    @Override
    public String part2() {
        List<Long> scores = validLines.stream()
                .map(this::getScoreValidLines)
                .sorted(Long::compareTo)
                .collect(Collectors.toList());

        return "" + scores.get(scores.size() / 2);
    }

    private long getScoreValidLines(String line) {
        String remainingStr = getRemainingStr(line);

        long score = 0;
        for (char c : remainingStr.toCharArray()) {
            String s = String.valueOf(c);
            score = (score * 5L) + scores2.get(s);
        }

        return score;

    }

    private String getRemainingStr(String line) {
        String buffer = "";
        String remainingString = "";

        for (int i = line.length() - 1; i >= 0; i--) {
            String s = line.substring(i, i + 1);

            if (opening.containsKey(s)) {
                buffer = s + buffer;
            } else {
                if (buffer.length() > 0 && Objects.equals(closing.get(s), buffer.substring(0, 1))) {
                    buffer = buffer.substring(1);
                } else {
                    remainingString = remainingString + closing.get(s);
                }
            }
        }
        return remainingString;
    }


    private int getScore(String line) {
        String chunks = "";
        for (char c : line.toCharArray()) {
            String s = String.valueOf(c);
            if (openChars.contains(s)) {
                chunks = chunks + s;
            } else {
                String expected = closing.get(chunks.substring(chunks.length() - 1));
                if (expected.equals(s)) {
                    chunks = chunks.substring(0, chunks.length() - 1);
                } else {
                    return scores.get(s);
                }
            }
        }
        return 0;
    }
}