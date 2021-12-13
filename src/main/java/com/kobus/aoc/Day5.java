package com.kobus.aoc;

import java.io.IOException;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Advent of Code 2021 Solutions
 * Day 5: Hydrothermal Venture
 *
 * @author Kobus Pretorius
 */
public class Day5 extends AoCRunnable {

    public static void main(String[] args) throws IOException {
        new Day5("5").run(true);
        new Day5("5").run(false);
    }

    public Day5(String dayNumber) {
        super(dayNumber);
        debugging = true;
    }

    @Override
    public String part1() {
        return "" + getNumOfIntersections(
                () -> input.stream()
                        .map(Line::new)
                        .filter(Line::isNotDiagonal));
    }

    @Override
    public String part2() {
        return "" + getNumOfIntersections(() -> input.stream().map(Line::new));
    }

    private int getNumOfIntersections(Supplier<Stream<Line>> lineSup) {
        int maxX = lineSup.get().mapToInt(l -> Math.max(l.start.x, l.end.x)).max().getAsInt();
        int maxY = lineSup.get().mapToInt(l -> Math.max(l.start.y, l.end.y)).max().getAsInt();

        int[][] grid = new int[maxX + 1][maxY + 1]; // default value is 0

        // Plot the lines
        lineSup.get().forEach(l -> l.plot().forEach(c -> grid[c.x][c.y]++));

        int numOfIntersections = 0;
        for (int x = 0; x <= maxX; x++) {
            for (int y = 0; y <= maxY; y++) {
                numOfIntersections += grid[x][y] > 1 ? 1 : 0;
            }
        }
        return numOfIntersections;
    }


    public class Line {
        public Coord start;
        public Coord end;

        public Line(Coord start, Coord end) {
            this.start = start;
            this.end = end;
        }

        public Line(String lineStr) {
            String[] parts = lineStr.split(" ");
            this.start = new Coord(parts[0]);
            this.end = new Coord(parts[2]);
        }

        public boolean isNotDiagonal() {
            return this.start.x == this.end.x || this.start.y == this.end.y;
        }

        public List<Coord> plot() {
            int deltaX = this.end.x - this.start.x;
            int deltaY = this.end.y - this.start.y;

            int maxSteps = Math.max(Math.abs(deltaX), Math.abs(deltaY)) + 1;

            int mX = Integer.compare(deltaX, 0);
            int mY = Integer.compare(deltaY, 0);

            return IntStream.range(0, maxSteps).boxed()
                    .map(i -> new Coord(this.start.x + (mX * i), this.start.y + (mY * i)))
                    .collect(Collectors.toList());
        }
    }

    public record Coord(int x, int y) {

        public Coord(String coords) {
            this(Integer.parseInt(coords.split(",")[0]), Integer.parseInt(coords.split(",")[1]));
        }
    }
}