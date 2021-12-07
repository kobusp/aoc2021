package com.kobus.aoc;

import java.io.IOException;
import java.util.Arrays;
import java.util.function.*;
import java.util.stream.IntStream;

/**
 * Advent of Code 2021 Solutions
 * Day 7 Optimized
 *
 * @author Kobus Pretorius
 */
public class Day7b extends AoCRunnable {

    public static void main(String[] args) throws IOException {
        new Day7b("7b").run(true);
        new Day7b("7b").run(false);
    }

    public int[] crabArr;
    public int min = Integer.MAX_VALUE;
    public int max = 0;

    public Day7b(String dayNumber) {
        super(dayNumber);
        debugging = true;
    }

    @Override
    public String part1() {
        crabArr = Arrays.stream(input.get(0).split(",")).mapToInt(Integer::parseInt).toArray();
        max = Arrays.stream(crabArr).max().getAsInt();
        min = Arrays.stream(crabArr).min().getAsInt();

        return "" + getMinFuelCost((a, b) -> Math.abs(a - b));
    }

    @Override
    public String part2() {
        return "" + getMinFuelCost((a, b) -> gaussSum(Math.abs(a - b)));
    }

    public int gaussSum(int n) {
        return n * (n + 1) / 2;
    }

    private int getMinFuelCost(BiFunction<Integer, Integer, Integer> measureFuelCost) {
        int iMin = min;
        int iMax = max;

        while (true) {
            int halfWay = iMin + (iMax - iMin) / 2;

            var f = IntStream.range(0, 3)
                    .map(i -> Arrays.stream(crabArr)
                            .map(c -> measureFuelCost.apply(halfWay + i - 1, c))
                            .sum())
                    .boxed()
                    .toList();

            int slope = Integer.compare(f.get(0).compareTo(f.get(1)) + f.get(1).compareTo(f.get(2)), 0);
            if (slope == 0) {
                return f.get(1);
            }
            iMax = slope < 0 ? halfWay : iMax;
            iMin = slope > 0 ? halfWay : iMin;
        }
    }

}