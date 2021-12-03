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

    public static void main(String[] args) throws IOException {
        var allDays = new ArrayList<AoCRunnable>();
        allDays.add(new Day1("1"));
        allDays.add(new Day2("2"));
        allDays.add(new Day3("3"));
        allDays.add(new Day4("4"));

        for (var day : allDays) {
            day.run(false);
        }
    }

    public AoC() {
    }
}
