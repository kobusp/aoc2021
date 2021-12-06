package com.kobus.aoc;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Advent of Code 2021 Solutions
 * Run all solutions with the proper input excluding test runs.
 *
 * @author Kobus Pretorius
 */
public class AoC {

    public static final int NUM_EXECUTIONS = 1000;

    public static void main(String[] args) throws IOException {
        System.out.println("===================== AoC 2021 ~ Batch Execution ===========");
        System.out.println("Part          Answer   Ave. of " + NUM_EXECUTIONS + " Runs\t\t\t (Worst)");
        var allDays = new ArrayList<AoCRunnable>();
        allDays.add(new Day1("1"));
        allDays.add(new Day2("2"));
        allDays.add(new Day3("3"));
        allDays.add(new Day4("4"));
        allDays.add(new Day5("5"));
        allDays.add(new Day6("6"));
        allDays.add(new Day7("7"));

        for (var day : allDays) {
            day.run(false, NUM_EXECUTIONS);
        }
    }

    public AoC() {
    }
}
