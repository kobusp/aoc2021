package com.kobus.aoc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Advent of Code 2021 Solutions
 * Day 13: Transparent Origami
 *
 * @author Kobus Pretorius
 */
public class Day13 extends AoCRunnable {

    public static void main(String[] args) throws IOException {
        new Day13("13").run(true);
        new Day13("13").run(false);
    }

    public int sizeX = 0;
    public int sizeY = 0;

    List<Coord> coords = new ArrayList<>();
    List<FoldInstruction> foldInstructions = new ArrayList<>();

    public Day13(String dayNumber) {
        super(dayNumber);
        debugging = true;
    }

    @Override
    public String part1() {
        int[][] grid = parseInput();
        grid = fold(grid, foldInstructions.get(0));

        return "" + countDots(grid);
    }


    @Override
    public String part2() {
        int[][] grid = parseInput();
        for (var instr : foldInstructions) {
            grid = fold(grid, instr);
        }
//        printGrid(grid);
        return "See grid output";
    }

    private int[][] parseInput() {
        boolean readingCoords = true;
        foldInstructions = new ArrayList<>();
        for (var line : input) {
            if (line.equals("")) {
                readingCoords = false;
                continue;
            }

            if (readingCoords) {
                coords.add(new Coord(Integer.parseInt(line.split(",")[0]), Integer.parseInt(line.split(",")[1])));
            } else {
                String instr = line.split(" ")[2];
                foldInstructions.add(new FoldInstruction(instr.split("=")[0], Integer.parseInt(instr.split("=")[1])));
            }
        }

        sizeX = coords.stream().mapToInt(c -> c.x).max().getAsInt() + 1;
        sizeY = coords.stream().mapToInt(c -> c.y).max().getAsInt() + 1;
        return plot(coords);
    }

    private int countDots(int[][] grid) {
        int answer = 0;
        for (var y = 0; y < sizeY; y++) {
            for (var x = 0; x < sizeX; x++) {
                if (grid[x][y] == 1) {
                    answer++;
                }
            }
        }
        return answer;
    }

    private void printGrid(int[][] grid) {
        for (var y = 0; y < sizeY; y++) {
            for (var x = 0; x < sizeX; x++) {
                if (grid[x][y] >= 0) {
                    print("" + (grid[x][y] == 1 ? "#" : " "));
                }
            }
            println("");
            if (y < sizeY - 1) {
                if (grid[0][y + 1] == -1) {
                    break;
                }
            }
        }
    }

    private int[][] plot(List<Coord> coords) {
        var grid = new int[sizeX][sizeY];
        for (var c : coords) {
            grid[c.x][c.y] = 1;
        }
        return grid;
    }

    private int[][] fold(int[][] grid, FoldInstruction foldInstruction) {
        var folded = new int[sizeX][sizeY];

        if (foldInstruction.axis.equals("x")) {
            for (var y = 0; y < sizeY; y++) {
                for (var x = 0; x < sizeX; x++) {

                    if (x < foldInstruction.n) {
                        folded[x][y] = Integer.max(grid[x][y], grid[foldInstruction.n - (x - foldInstruction.n)][y]);
                    } else {
                        folded[x][y] = -1;
                    }

                }
            }

        } else if (foldInstruction.axis.equals("y")) {
            for (var y = 0; y < sizeY; y++) {
                for (var x = 0; x < sizeX; x++) {

                    if (y < foldInstruction.n) {
                        folded[x][y] = Integer.max(grid[x][y], grid[x][foldInstruction.n - (y - foldInstruction.n)]);
                    } else {
                        folded[x][y] = -1;
                    }

                }
            }
        }
        return folded;
    }


    public record Coord(int x, int y) {
    }

    public record FoldInstruction(String axis, int n) {
    }

}