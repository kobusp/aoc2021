package com.kobus.aoc;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Advent of Code 2021 Solutions
 * Day 17: Trick Shot
 *
 * @author Kobus Pretorius
 */
public class Day17 extends AoCRunnable {

    public static void main(String[] args) throws IOException {
        new Day17("17").run(true);
        new Day17("17").run(false);
    }

    public Day17(String dayNumber) {
        super(dayNumber);
        debugging = true;
    }

    @Override
    public String part1() {
        Area area = parseInput(input.get(0));

        int maxHeight = 0;

        for (int x = 1; x < area.bottomLeft.x; x++) {
            for (int y = 1; y < 1000; y++) { // 5460

                var velocity = new Coord(x, y);
                int height = heighestHeight(velocity, area);
                if (height > 0) {
                    if (height > maxHeight) {
                        maxHeight = height;
                    }
                }
            }
        }

        return "" + maxHeight;
    }

    @Override
    public String part2() {
        Area area = parseInput(input.get(0));

        Set<Coord> distinctStartingCoords = new HashSet<>();

        for (int x = 1; x <= area.topRight.x; x++) {
            for (int y = area.bottomLeft.y; y < 1000; y++) { // 5460

                var velocity = new Coord(x, y);
                if (willHit(velocity, area)) {
                    distinctStartingCoords.add(new Coord(x, y));
                }
            }
        }

        return "" + distinctStartingCoords.size();
    }

    private int heighestHeight(Coord velocity, Area area) {
        Coord position = new Coord(0, 0);
        int maxHeight = 0;
        while (position.x <= area.topRight.x && position.y >= area.bottomLeft.y) {
            position.x += velocity.x;
            position.y += velocity.y;

            if (position.y > maxHeight) {
                maxHeight = position.y;
            }

            velocity.x -= 1;
            velocity.y -= 1;
            if (velocity.x < 0) {
                velocity.x = 0;
            }

            if (area.inArea(position)) {
                return maxHeight;
            }
        }
        return 0;
    }

    private boolean willHit(Coord velocity, Area area) {
        Coord position = new Coord(0, 0);
        while (position.x <= area.topRight.x && position.y >= area.bottomLeft.y) {
            position.x += velocity.x;
            position.y += velocity.y;

            velocity.x -= 1;
            velocity.y -= 1;
            if (velocity.x < 0) {
                velocity.x = 0;
            }

            if (area.inArea(position)) {
                return true;
            }
        }
        return false;
    }

    private Area parseInput(String line) {
        String[] parts = line.split(" ");
        var part1 = Arrays.stream(parts[2].substring(2, parts[2].length() - 1).replace("..", " ").split(" ")).mapToInt(Integer::parseInt).boxed().collect(Collectors.toList());
        var part2 = Arrays.stream(parts[3].substring(2).replace("..", " ").split(" ")).mapToInt(Integer::parseInt).boxed().collect(Collectors.toList());
        Coord bottomLeft = new Coord(part1.get(0), part2.get(0));
        Coord topRight = new Coord(part1.get(1), part2.get(1));

        return new Area(bottomLeft, topRight);
    }

    public class Area {
        public Coord bottomLeft;
        public Coord topRight;

        public Area(Coord bottomLeft, Coord topRight) {
            this.bottomLeft = bottomLeft;
            this.topRight = topRight;
        }

        boolean inArea(Coord coord) {
            return coord.x >= bottomLeft.x && coord.x <= topRight.x
                    && coord.y >= bottomLeft.y && coord.y <= topRight.y;
        }

        @Override
        public String toString() {
            return "Area{" +
                    "bottomLeft=" + bottomLeft +
                    ", topRight=" + topRight +
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

        @Override
        public String toString() {
            return "Coord{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }
    }
}