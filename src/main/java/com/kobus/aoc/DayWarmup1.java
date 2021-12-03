package com.kobus.aoc;

import java.io.IOException;
import java.util.Objects;

/**
 * Advent of Code 2021 Solutions
 * Warmup Day 1
 *
 * @author Kobus Pretorius
 */
public class DayWarmup1 extends AoCRunnable {

    public static void main(String[] args) throws IOException {
        new DayWarmup2("warmup1").run(true);
        new DayWarmup2("warmup1").run(false);
    }

    public DayWarmup1(String dayNumber) {
        super(dayNumber);
    }

    @Override
    public String part1() {
        int answer = 0;
        parseInputAsInt();
        for (var num1 : inputAsInt) {
            for (var num2 : inputAsInt) {
                if (!Objects.equals(num1, num2) && (num1 + num2 == 2020)) {
                    answer = num1 * num2;
                }
            }
        }
        return "" + answer;
    }

    @Override
    public String part2() {
        int answer = 0;
        parseInputAsInt();
        for (var num1 : inputAsInt) {
            for (var num2 : inputAsInt) {
                for (var num3 : inputAsInt) {
                    if (!Objects.equals(num1, num2) && !Objects.equals(num2, num3) && !Objects.equals(num3, num1) && (num1 + num2 + num3 == 2020)) {
                        answer = num1 * num2 * num3;
                    }
                }
            }
        }
        return "" + answer;
    }
}
