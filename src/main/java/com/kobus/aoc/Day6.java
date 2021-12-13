package com.kobus.aoc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Advent of Code 2021 Solutions
 * Day 6: Lanternfish
 *
 * @author Kobus Pretorius
 */
public class Day6 extends AoCRunnable {

    public static void main(String[] args) throws IOException {
        new Day6("6").run(true);
        new Day6("6").run(false);
    }

    public Day6(String dayNumber) {
        super(dayNumber);
        debugging = true;
    }

    @Override
    public String part1() {
        return "" + calculateForNDays(80);
    }


    @Override
    public String part2() {
        return "" + calculateForNDays(256);
    }

    private long calculateForNDays(int numDays) {
        var fishes = new ArrayList<>(Arrays.stream(input.get(0).split(",")).mapToInt(Integer::parseInt).boxed().toList());
        List<Long> pattern = new ArrayList<>();

        for (int i = 0; i < 9; i++) {
            int finalI = i;
            long n = fishes.stream().filter(f -> f.longValue() == finalI).count();
            pattern.add(n);
        }

        for (int i = 0; i < numDays; i++) {
            long toAdd = pattern.get(0);
            for (int j = 0; j < 8; j++) {
                pattern.set(j, pattern.get(j + 1));
            }
            pattern.set(6, pattern.get(6) + toAdd);
            pattern.set(8, toAdd);
        }

        return pattern.stream().mapToLong(Long::longValue).sum();
    }
}