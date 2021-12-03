package com.kobus.aoc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Advent of Code 2021 Solutions
 * Warmup Day 2
 *
 * @author Kobus Pretorius
 */
public class DayWarmup2 extends AoCRunnable {

    public static void main(String[] args) throws IOException {
        new DayWarmup2("warmup2").run(true);
        new DayWarmup2("warmup2").run(false);
    }

    public DayWarmup2(String dayNumber) {
        super(dayNumber);
    }

    @Override
    public String part1() {
        var groupAnswers = new ArrayList<Integer>();
        var yesAnswers = new HashSet<String>();

        input.add(""); // Add one empty line to streamline the logic
        for (String line : input) {
            if (line.isEmpty()) {
                groupAnswers.add(yesAnswers.size());
                yesAnswers = new HashSet<>();
            } else {
                for (char c : line.toCharArray()) {
                    yesAnswers.add(String.valueOf(c));
                }
            }
        }

        return "" + groupAnswers.stream().reduce(0, Integer::sum);
    }

    @Override
    public String part2() {
        var groupAnswers = new ArrayList<Integer>();
        var yesAnswers = new HashMap<String, Integer>();

        int numInGroup = 0;


        input.add(""); // Add one empty line to streamline the logic
        for (String line : input) {
            if (line.isEmpty()) {

                var yesInAll = 0;
                for (var s : yesAnswers.keySet()) {
                    if (yesAnswers.get(s) == numInGroup) {
                        yesInAll++;
                    }
                }
                groupAnswers.add(yesInAll);

                yesAnswers = new HashMap<>();
                numInGroup = 0;
            } else {
                for (char c : line.toCharArray()) {
                    var s = String.valueOf(c);
                    yesAnswers.put(s, yesAnswers.get(s) == null ? 1 : yesAnswers.get(s) + 1);
                }
                numInGroup++;
            }
        }

        return "" + groupAnswers.stream().reduce(0, Integer::sum);
    }
}
