package com.kobus.aoc;

import java.io.IOException;

/**
 * Advent of Code 2021 Solutions
 * Day 2: Dive!
 *
 * @author Kobus Pretorius
 */
public class Day2 extends AoCRunnable {

    public static void main(String[] args) throws IOException {
        new Day2("2").run(true);
        new Day2("2").run(false);
    }

    public Day2(String dayNumber) {
        super(dayNumber);
    }

    @Override
    public String part1() {
        int pos = 0;
        int depth = 0;

        var parsedInput = parse(String.class, Integer.class);

        for (var line : parsedInput) {
            var instr = (String) line.get(0);
            var intVal = (Integer) line.get(1);
            if (instr.equals("forward")) {
                pos += intVal;
            }
            if (instr.equals("down")) {
                depth += intVal;
            }
            if (instr.equals("up")) {
                depth -= intVal;
            }
        }

        return "" + pos * depth;
    }

    @Override
    public String part2() {
        int pos = 0;
        int depth = 0;
        int aim = 0;

        var parsedInput = parse(String.class, Integer.class);

        for (var line : parsedInput) {
            var instr = (String) line.get(0);
            var intVal = (Integer) line.get(1);
            if (instr.equals("forward")) {
                pos += intVal;
                depth += (aim * intVal);
            } else if (instr.equals("down")) {
                aim += intVal;
            } else {
                aim -= intVal;
            }
        }

        return "" + pos * depth;
    }
}
