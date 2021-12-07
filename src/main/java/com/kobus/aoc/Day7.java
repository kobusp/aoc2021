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

    public List<Integer> crabs = new ArrayList<>();
    public Supplier<IntStream> crabSupplier = null;
    public int min = Integer.MAX_VALUE;
    public int max = 0;


    public Day7(String dayNumber) {
        super(dayNumber);
        debugging = true;
    }

    @Override
    public String part1() {
        crabs = new ArrayList<>(Arrays.stream(input.get(0).split(",")).mapToInt(Integer::parseInt).boxed().toList());
        crabSupplier = () -> crabs.stream().mapToInt(Integer::intValue);

        for (Integer i : crabs) {
            if (min > i) {
                min = i;
            }
            if (max < i) {
                max = i;
            }
        }

        int minFuelCost = -1;
        for (int i = min; i <= max; i++) {

            int finalI = i;
            int fuelCost = crabSupplier.get().map(c -> Math.abs(finalI - c)).sum();
            if (minFuelCost == -1 || fuelCost < minFuelCost) {
                minFuelCost = fuelCost;
            }

            if (minFuelCost > 0 && fuelCost > minFuelCost) {
                break;
            }
        }

        return "" + minFuelCost;
    }

    @Override
    public String part2() {
        int minFuelCost = -1;
        for (int i = min; i <= max; i++) {

            int finalI = i;

            int fuelCost = crabSupplier.get().map(c -> gaussSum(Math.abs(finalI - c))).sum();
            if (minFuelCost == -1 || fuelCost < minFuelCost) {
                minFuelCost = fuelCost;
            }

            if (minFuelCost > 0 && fuelCost > minFuelCost) {
                break;
            }
        }

        return "" + minFuelCost;
    }

    public int gaussSum(int n) {
        return n * (n + 1) / 2;
    }
}