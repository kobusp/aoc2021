package com.kobus.aoc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Advent of Code 2021 Solutions
 * Day 9
 *
 * @author Kobus Pretorius
 */
public class Day9 extends AoCRunnable {

    public static void main(String[] args) throws IOException {
        new Day9("9").run(true);
        new Day9("9").run(false);
    }

    private int[][] grid;
    private int sizeX;
    private int sizeY;

    public Day9(String dayNumber) {
        super(dayNumber);
        debugging = true;
    }

    @Override
    public String part1() {

        grid = parseInput();

        int riskLevel = 0;
        for (int y = 0; y < sizeY; y++) {
            for (int x = 0; x < sizeX; x++) {
                if (neighboursAreLarger(x, y)) {
                    riskLevel += grid[y][x] + 1;
                }
            }
        }

        return "" + riskLevel;
    }


    @Override
    public String part2() {
        HashMap<Coord, Integer> basins = new HashMap<>();

        for (int y = 0; y < sizeY; y++) {
            for (int x = 0; x < sizeX; x++) {

                if (grid[y][x] < 9) {
                    Coord lowPoint = findLowPoint(x, y);
                    basins.merge(lowPoint, 1, Integer::sum);
                }
            }
        }

        List<Integer> basinSizes = basins.values().stream().sorted(Collections.reverseOrder()).collect(Collectors.toList());
        return "" + (basinSizes.get(0) * basinSizes.get(1) * basinSizes.get(2));
    }

    private Coord findLowPoint(int x, int y) {
        int v = grid[y][x];
        if (x > 0) {
            if (grid[y][x - 1] < v) {
                return findLowPoint(x - 1, y);
            }
        }
        if (y > 0) {
            if (grid[y - 1][x] < v) {
                return findLowPoint(x, y - 1);
            }
        }
        if (x < sizeX - 1) {
            if (grid[y][x + 1] < v) {
                return findLowPoint(x + 1, y);
            }
        }
        if (y < sizeY - 1) {
            if (grid[y + 1][x] < v) {
                return findLowPoint(x, y + 1);
            }
        }
        return new Coord(x, y);
    }

    private boolean neighboursAreLarger(int x, int y) {
        int v = grid[y][x];
        var neighbours = new ArrayList<Integer>();
        if (x > 0) {
            neighbours.add(grid[y][x - 1]);
        }
        if (y > 0) {
            neighbours.add(grid[y - 1][x]);
        }
        if (x < sizeX - 1) {
            neighbours.add(grid[y][x + 1]);
        }
        if (y < sizeY - 1) {
            neighbours.add(grid[y + 1][x]);
        }
        for (int n : neighbours) {
            if (v >= n) {
                return false;
            }
        }
        return true;
    }

    private int[][] parseInput() {
        int x = input.get(0).length();
        int y = input.size();

        sizeX = x;
        sizeY = y;

        int[][] inputGrid = new int[y][x];

        int iy = 0;
        for (var line : input) {

            for (int ix = 0; ix < x; ix++) {
                inputGrid[iy][ix] = Integer.parseInt(String.valueOf(line.charAt(ix)));
            }
            iy++;
        }

        return inputGrid;
    }

    public record Coord(int x, int y) {

        public Coord(String coords) {
            this(Integer.parseInt(coords.split(",")[0]), Integer.parseInt(coords.split(",")[1]));
        }
    }
}