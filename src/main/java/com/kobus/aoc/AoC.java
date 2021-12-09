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
        System.out.println("");
        System.out.println("╔═══════════════════ AoC 2021 ~ Batch Execution ═══════════════════╗");
        System.out.println("║ Day  Part           Answer" + String.format("%20s", "Ave. of " + NUM_EXECUTIONS + " Runs") + String.format("%18s", "(Worst)") + " ║");
        var allDays = new ArrayList<AoCRunnable>();
        allDays.add(new Day1("1"));
        allDays.add(new Day2("2"));
        allDays.add(new Day3("3"));
        allDays.add(new Day4("4"));
//        allDays.add(new Day5("5"));
        allDays.add(new Day5b("5b"));
        allDays.add(new Day6("6"));
//        allDays.add(new Day7("7"));
        allDays.add(new Day7b("7b"));
        allDays.add(new Day8("8"));
        allDays.add(new Day9("9"));
        allDays.add(new Day10("10"));

        for (var day : allDays) {
            day.run(false, NUM_EXECUTIONS);
        }

        System.out.println("╚══════════════════════════════════════════════════════════════════╝");
    }

    public AoC() {
    }
}
