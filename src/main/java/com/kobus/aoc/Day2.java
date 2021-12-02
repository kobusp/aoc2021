package com.kobus.aoc;

import java.io.IOException;

public class Day2 extends AoCRunnable {

    public static void main(String[] args) throws IOException {
        (new AoC()).run(new Day2(),
                "2",
                false);
    }

    @Override
    public String part1() {
        int pos = 0;
        int depth = 0;
        int answer;

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

        answer = pos * depth;

        return "" + answer;
    }

    @Override
    public String part2() {
        int pos = 0;
        int depth = 0;
        int aim = 0;
        int answer;

        var parsedInput = parse(String.class, Integer.class);

        for (var line : parsedInput) {
            var instr = (String) line.get(0);
            var intVal = (Integer) line.get(1);
            if (instr.equals("forward")) {
                pos += intVal;
                depth += (aim * intVal);
            }
            if (instr.equals("down")) {
                aim += intVal;
            }
            if (instr.equals("up")) {
                aim -= intVal;
            }
        }

        answer = pos * depth;

        return "" + answer;
    }
}
