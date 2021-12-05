package com.kobus.aoc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Advent of Code 2021 Solutions
 * Day 5
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
        debugging = false;
    }

    @Override
    public String part1() {
        List<Line> horzVertLines = readInput().stream().filter(Line::isNotDiagonal).collect(Collectors.toList());
        return "" + getNumOfIntersections(horzVertLines);
    }

    @Override
    public String part2() {
        return "" + getNumOfIntersections(readInput());
    }


    private ArrayList<Line> readInput() {
        var lines = new ArrayList<Line>();
        for (var inputLine : input) {
            var line = new Line(inputLine);
            lines.add(line);
        }
        return lines;
    }

    private int getNumOfIntersections(List<Line> lines) {
        int maxX = 0;
        int maxY = 0;

        for (var line : lines) {
            if (line.start.x > maxX) {
                maxX = line.start.x;
            }
            if (line.end.x > maxX) {
                maxX = line.end.x;
            }

            if (line.start.y > maxY) {
                maxY = line.start.y;
            }
            if (line.end.y > maxY) {
                maxY = line.end.y;
            }
        }

        int[][] grid = new int[maxX + 1][maxY + 1];

        // Init grid
        for (int x = 0; x <= maxX; x++) {
            for (int y = 0; y <= maxY; y++) {
                grid[x][y] = 0;
            }
        }

        // Plot the lines
        for (var line : lines) {
            var coords = line.plot();
            for (var coord : coords) {
                grid[coord.x][coord.y]++;
            }
        }

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
            return this.start.x == this.end.x
                    || this.start.y == this.end.y;
        }

        public List<Coord> plot() {
            int deltaX = this.end.x - this.start.x;
            int deltaY = this.end.y - this.start.y;

            int maxSteps = Math.max(Math.abs(deltaX), Math.abs(deltaY)) + 1;
            var coords = new ArrayList<Coord>();

            int mX = Integer.compare(deltaX, 0);
            int mY = Integer.compare(deltaY, 0);

            for (int i = 0; i < maxSteps; i++) {
                coords.add(new Coord(this.start.x + (mX * i), this.start.y + (mY * i)));
            }

            return coords;
        }

        @Override
        public String toString() {
            return "Line{" +
                    "start=" + start +
                    ", end=" + end +
                    '}';
        }
    }

    public class Coord {
        public int x;
        public int y;

        public Coord(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public Coord(String coords) {
            var coordList = Arrays.stream(coords.split(",")).mapToInt(Day5.this::parseInt).boxed().collect(Collectors.toList());
            this.x = coordList.get(0);
            this.y = coordList.get(1);
        }

        @Override
        public String toString() {
            return "Coord{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }
    }
}