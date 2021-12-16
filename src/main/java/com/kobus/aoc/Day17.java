package com.kobus.aoc;

import java.io.IOException;

/**
 * Advent of Code 2021 Solutions
 * Day 17
 *
 * @author Kobus Pretorius
 */
public class Day17 extends AoCRunnable {

    public static void main(String[] args) throws IOException {
        new Day17("17").run(true);
        new Day17("17").run(false);
    }

    public Day17(String dayNumber) {
        super(dayNumber);
        debugging = true;
    }

    @Override
    public String part1() {
        int answer = 0;
        for (var line : input) {

        }
        return "" + answer;
    }

    @Override
    public String part2() {
        return "";
    }
}