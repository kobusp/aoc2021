package com.kobus.aoc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.IntStream;

/**
 * Advent of Code 2021 Solutions
 * Day 7
 *
 * @author Kobus Pretorius
 */
public class Day7 extends AoCRunnable {

    public static void main(String[] args) throws IOException {
        new Day7("7").run(true);
        new Day7("7").run(false);
    }

    public Day7(String dayNumber) {
        super(dayNumber);
        debugging = true;
    }

    @Override
    public String part1() {
        List<Integer> crabs = new ArrayList<>(Arrays.stream(input.get(0).split(",")).mapToInt(Integer::parseInt).boxed().toList());

        int min = Integer.MAX_VALUE;
        int max = 0;
        for (Integer i : crabs) {
            if (min > i) {
                min = i;
            }
            if (max < i) {
                max = i;
            }
        }
        Supplier<IntStream> sup = () -> crabs.stream().mapToInt(Integer::intValue);

        int minFuelCost = -1;
        for (int i = min; i <= max; i++) {

            int finalI = i;
            int fuelCost = sup.get().map(c -> Math.abs(finalI - c)).sum();
            if (minFuelCost == -1 || fuelCost < minFuelCost) {
                minFuelCost = fuelCost;
            }
        }

        return "" + minFuelCost;
    }

    @Override
    public String part2() {
        List<Integer> crabs = new ArrayList<>(Arrays.stream(input.get(0).split(",")).mapToInt(Integer::parseInt).boxed().toList());

        int min = Integer.MAX_VALUE;
        int max = 0;
        for (Integer i : crabs) {
            if (min > i) {
                min = i;
            }
            if (max < i) {
                max = i;
            }
        }
        List<Integer> cachedDistances = precalculateDistance(max);
        Supplier<IntStream> sup = () -> crabs.stream().mapToInt(Integer::intValue);

        int minFuelCost = -1;
        for (int i = min; i <= max; i++) {

            int finalI = i;
            int fuelCost = sup.get().map(c -> cachedDistances.get(Math.abs(finalI - c))).sum();
            if (minFuelCost == -1 || fuelCost < minFuelCost) {
                minFuelCost = fuelCost;
            }
        }

        return "" + minFuelCost;
    }

    public List<Integer> precalculateDistance(int max) {
        var precalcDistances = new ArrayList<Integer>();
        precalcDistances.add(0);
        int sum = 0;
        for (int n = 1; n <= max; n++) {
            sum += n;
            precalcDistances.add(sum);
        }
        return precalcDistances;
    }
}