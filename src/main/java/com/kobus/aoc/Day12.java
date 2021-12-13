package com.kobus.aoc;

import java.io.IOException;
import java.util.*;

/**
 * Advent of Code 2021 Solutions
 * Day 12: Passage Pathing
 *
 * @author Kobus Pretorius
 */
public class Day12 extends AoCRunnable {

    public static void main(String[] args) throws IOException {
        new Day12("12").run(true);
        new Day12("12").run(false);
    }

    private String start = "start";
    private String end = "end";
    private CaveSystem caveSystem;


    public Day12(String dayNumber) {
        super(dayNumber);
        debugging = true;
    }

    @Override
    public String part1() {
        caveSystem = new CaveSystem(input);
        return "" + caveSystem.numPaths(new Cave(start, true, true, false), new ArrayList<>());
    }

    @Override
    public String part2() {
//        var caveSystem = new CaveSystem(input);
        return "" + caveSystem.numPaths2(new Cave(start, true, true, false), new ArrayList<>(), false);
    }

    public class CaveSystem {
        public Map<Cave, List<Cave>> neighbours;

        public CaveSystem(List<String> input) {
            this.neighbours = new HashMap<>();
            for (var line : input) {
                var caveA = line.split("-")[0];
                var caveB = line.split("-")[1];

                this.addCave(caveA);
                this.addCave(caveB);
                this.addEdge(caveA, caveB);
            }
        }

        public void addCave(String label) {
            neighbours.putIfAbsent(new Cave(label, label.toLowerCase().equals(label), label.equals("start"), label.equals("end")), new ArrayList<>());
        }

        public void addEdge(String label1, String label2) {
            Cave c1 = new Cave(label1, label1.toLowerCase().equals(label1), label1.equals("start"), label1.equals("end"));
            Cave c2 = new Cave(label2, label2.toLowerCase().equals(label2), label2.equals("start"), label2.equals("end"));
            neighbours.get(c1).add(c2);
            neighbours.get(c2).add(c1);
        }

        public int numPaths(Cave cave, List<String> visited) {
            int num = 0;
            visited.add(cave.label);

            if (cave.label.equals("end")) {
                return 1;
            }

            for (var c : neighbours.get(cave)) {
                if (!(c.isSmall() && visited.contains(c.label))) {
                    num += numPaths(c, new ArrayList<>(visited));
                }
            }

            return num;
        }

        public int numPaths2(Cave cave, List<String> visited, boolean hasVisitedSmallCaveTwice) {
            int num = 0;

            if (cave.isEnd) {
                return 1;
            }
            if (cave.isSmall()) {
                visited.add(cave.label);
            }

            for (var c : neighbours.get(cave)) {
                if (!c.isStart) {
                    if (c.isSmall()) {
                        if (!visited.contains(c.label)) {
                            num += numPaths2(c, new ArrayList<>(visited), hasVisitedSmallCaveTwice);
                        } else if (!hasVisitedSmallCaveTwice) {
                            num += numPaths2(c, new ArrayList<>(visited), true);
                        }
                    } else {
                        num += numPaths2(c, new ArrayList<>(visited), hasVisitedSmallCaveTwice);
                    }
                }
            }
            return num;
        }
    }

    public record Cave(String label, boolean isSmall, boolean isStart, boolean isEnd) {
    }
}