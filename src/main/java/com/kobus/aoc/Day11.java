package com.kobus.aoc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Advent of Code 2021 Solutions
 * Day 11: Dumbo Octopus
 *
 * @author Kobus Pretorius
 */
public class Day11 extends AoCRunnable {

    public static void main(String[] args) throws IOException {
        new Day11("11").run(true);
        new Day11("11").run(false);
    }

    int sizeX = 0;
    int sizeY = 0;
    int[][] octopusses;


    public Day11(String dayNumber) {
        super(dayNumber);
        debugging = false;

    }

    @Override
    public String part1() {

        octopusses = parseInput();


        println("sizeX " + sizeX + ", sizeY " + sizeY);


        printGrid(octopusses);

        int numFlashes = 0;

        for (int n = 0; n < 100; n++) {
            numFlashes += getNumFlashes();
        }

        println("Output");
        printGrid(octopusses);

        return "" + numFlashes;
    }

    private int getNumFlashes() {

        int[][] energyAdded = new int[sizeY][sizeX];
        boolean[][] flashed = new boolean[sizeY][sizeX];
        int numFlashes = 0;

        for (int x = 0; x < sizeX; x++) {
            for (int y = 0; y < sizeY; y++) {
                energyAdded[y][x] = 1;
                flashed[y][x] = false;
            }
        }

        for (int n = 0; n < 20; n++) {
            for (int x = 0; x < sizeX; x++) {
                for (int y = 0; y < sizeY; y++) {

                    if (octopusses[y][x] + energyAdded[y][x] > 9 && !flashed[y][x]) {
                        numFlashes++;
                        flashed[y][x] = true;
                        for (int i = -1; i < 2; i++) {
                            for (int j = -1; j < 2; j++) {
                                int nX = x + i;
                                int nY = y + j;

                                if (!(i == 0 && j == 0) && nX >= 0 && nX < sizeX && nY >= 0 && nY < sizeY) {
//                                    println("n (" + nX + ", " + nY + ")");
                                    energyAdded[nY][nX]++;
                                }
                            }
                        }
                    }
                }
            }

        }

        for (int x = 0; x < sizeX; x++) {
            for (int y = 0; y < sizeY; y++) {
                int totalEnergy = octopusses[y][x] + energyAdded[y][x];
                octopusses[y][x] = totalEnergy > 9 ? 0 : totalEnergy;
            }
        }

//        println("Energy added:");
//        printGrid(energyAdded);
//        println("");
        return numFlashes;
    }

    private void printGrid(int[][] grid) {
        for (int y = 0; y < sizeY; y++) {
            for (int x = 0; x < sizeX; x++) {
                print(grid[y][x] + "");
            }
            println("");
        }
    }


    @Override
    public String part2() {
        octopusses = parseInput();

        println("sizeX " + sizeX + ", sizeY " + sizeY);

        printGrid(octopusses);

        int numFlashes = 0;

        int n = 0;
        while(numFlashes < sizeX * sizeY) {
            numFlashes = getNumFlashes();
            n++;
        }

        println("Output");
        printGrid(octopusses);

        return "" + n;
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

}