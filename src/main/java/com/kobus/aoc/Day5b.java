package com.kobus.aoc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Advent of Code 2021 Solutions
 * Day 5 Optimized
 *
 * @author Kobus Pretorius
 */
public class Day5b extends AoCRunnable {

    public static void main(String[] args) throws IOException {
        new Day5b("5b").run(true);
        new Day5b("5b").run(false);
    }

    private List<Line> lines;

    public Day5b(String dayNumber) {
        super(dayNumber);
        debugging = true;
    }

    @Override
    public String part1() {
        lines = input.stream().map(Line::new).collect(Collectors.toList());

        return "" + getNumOfIntersections(
                lines.stream()
                        .filter(Line::isNotDiagonal).collect(Collectors.toList()));
    }

    @Override
    public String part2() {
        return "" + getNumOfIntersections(lines);
    }

    private int getNumOfIntersections(List<Line> plotLines) {
        int maxX = 1000;
        int maxY = 1000;

        int[] grid = new int[maxX * maxY];
        var intersections = new HashSet<Integer>();

        for (var line : plotLines) {
            List<int[]> coords = line.plot();
            for (var c : coords) {
                if (++grid[c[0] * maxX + c[1]] > 1) {
                    intersections.add(c[0] * maxX + c[1]);
                }
            }
        }

        return intersections.size();
    }


    public class Line {
        public int[] start;
        public int[] end;

        public Line(int[] start, int[] end) {
            this.start = start;
            this.end = end;
        }

        public Line(String lineStr) {
            String[] parts = lineStr.split(" ");
            this.start = Arrays.stream(parts[0].split(",")).mapToInt(Integer::parseInt).toArray();
            this.end = Arrays.stream(parts[2].split(",")).mapToInt(Integer::parseInt).toArray();
        }

        public boolean isNotDiagonal() {
            return this.start[0] == this.end[0] || this.start[1] == this.end[1];
        }

        public List<int[]> plot() {
            int deltaX = this.end[0] - this.start[0];
            int deltaY = this.end[1] - this.start[1];

            int maxSteps = Math.max(Math.abs(deltaX), Math.abs(deltaY)) + 1;

            int mX = Integer.compare(deltaX, 0);
            int mY = Integer.compare(deltaY, 0);

            List<int[]> coords = new ArrayList<>();
            for (int i = 0; i < maxSteps; i++) {
                coords.add(new int[]{this.start[0] + (mX * i), this.start[1] + (mY * i)});
            }

            return coords;
        }

    }
}