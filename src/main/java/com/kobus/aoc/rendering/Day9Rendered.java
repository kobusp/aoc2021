package com.kobus.aoc.rendering;

import com.kobus.aoc.AoCRunnable;
import com.kobus.aoc.Day9;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Day9Rendered extends JFrame {

    private int[][] grid;
    private int sizeX;
    private int sizeY;
    private List<Day9.Coord> directions = new ArrayList<Day9.Coord>();
    private List<Color> colors;

    public static void main(String[] args) throws Exception {

        SwingUtilities.invokeLater(() -> {
            try {
                new Day9Rendered().setVisible(true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }


    public Day9Rendered() throws IOException {
        super("AoC 2021 - Day 9 Smoke Basin");

        grid = parseInput();

        colors = List.of(
                Color.decode("#5555FF"),     // 0
                Color.decode("#6666FF"),     // 1
                Color.decode("#7777FF"),     // 2
                Color.decode("#8888FF"),     // 3
                Color.decode("#9999FF"),     // 4
                Color.decode("#AAAAFF"),     // 5
                Color.decode("#BBBBFF"),     // 6
                Color.decode("#CCCCFF"),     // 7
                Color.decode("#DDDDFF"),     // 8
                Color.decode("#FFAAFF"),     // 9
                Color.decode("#5555FF"),
                Color.decode("#5555FF")
        );

        getContentPane().setBackground(Color.WHITE);
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    void drawRectangles(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        for (int x = 0; x < sizeX; x++) {
            for (int y = 0; y < sizeX; y++) {

                g2d.setColor(colors.get(grid[y][x])); //use g2 instead
                g2d.fillRect(x * 4, y * 4, 4, 4);//use g2 instead
            }
        }

//        g2d.fillRect(x, x, 120, 60);//use g2 instead
//        repaint();

        try {
            Thread.sleep(10);
        } catch (InterruptedException ignored) {
        }
//        }

    }

    public void paint(Graphics g) {
        super.paint(g);

//        HashMap<Day9.Coord, Integer> basins = new HashMap<>();
//
//        for (int y = 0; y < sizeY; y++) {
//            for (int x = 0; x < sizeX; x++) {
//
//                if (grid[y][x] < 9) {
//                    Day9.Coord lowPoint = findLowPoint(x, y);
//                    basins.merge(lowPoint, 1, Integer::sum);
//                }
//            }
//        }
//
//        List<Integer> basinSizes = basins.values().stream().sorted(Collections.reverseOrder()).collect(Collectors.toList());


        drawRectangles(g);
    }

    private int[][] parseInput() throws IOException {
        var input = Files.readAllLines(Path.of("src/main/resources/day9.txt"));
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

    private Day9.Coord findLowPoint(int x, int y) {
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
        return new Day9.Coord(x, y);
    }

    public record Coord(int x, int y) {
    }
}
