package com.kobus.aoc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Advent of Code 2021 Solutions
 * Day n
 *
 * @author Kobus Pretorius
 */
public class DayTemplate extends AoCRunnable {

    public static void main(String[] args) throws IOException {
        new DayTemplate("n").run(true);
        new DayTemplate("n").run(false);
    }

    public DayTemplate(String dayNumber) {
        super(dayNumber);
        debugging = true;
    }

    @Override
    public String part1() {
        return "";
    }

    @Override
    public String part2() {
        return "";
    }
}