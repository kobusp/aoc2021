package com.kobus.aoc;

import java.io.IOException;

public class DayWarmup1 extends AoCRunnable {

    public static void main(String[] args) throws IOException {
        (new AoC()).run(new DayWarmup1(),
                "warmup1",
                2,
                false);
    }

    @Override
    public String part1() {
        int answer = 0;
        parseInputAsInt();
        for (var num1 : inputAsInt) {
            for (var num2 : inputAsInt) {
                if (num1 != num2 && (num1 + num2 == 2020)) {
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
                    if (num1 != num2 && num2 != num3 && num3 != num1 && (num1 + num2 + num3 == 2020)) {
                        answer = num1 * num2 * num3;
                    }
                }
            }
        }
        return "" + answer;
    }
}
