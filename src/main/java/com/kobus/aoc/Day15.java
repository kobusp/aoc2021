package com.kobus.aoc;

import java.io.IOException;
import java.util.*;

/**
 * Advent of Code 2021 Solutions
 * Day 15: Chiton
 *
 * @author Kobus Pretorius
 */
public class Day15 extends AoCRunnable {

    public static void main(String[] args) throws IOException {
        new Day15("15").run(true);
        new Day15("15").run(false);
    }


    public Day15(String dayNumber) {
        super(dayNumber);
        debugging = false;
    }

    @Override
    public String part1() {
        int[][] grid = parseInput();
        int sizeX = grid[0].length;
        int sizeY = grid.length;

        println("sizeX " + sizeX + ", sizeY " + sizeY);

        Graph graph = buildGraph(grid);
        getShortestPath(graph.getNode(0, 0));
        int answer = graph.getNode(sizeX - 1, sizeY - 1).risk;
        return "" + answer;
    }

    @Override
    public String part2() {
        int[][] initialGrid = parseInput();

        int initX = initialGrid[0].length;
        int initY = initialGrid.length;

        int sizeX = initialGrid[0].length * 5;
        int sizeY = initialGrid.length * 5;

        int[][] grid = new int[sizeX][sizeY];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                int xOffset = initX * i;
                int yOffset = initY * j;
                int nOffset = (i + j);
                for (int x = 0; x < initX; x++) {
                    for (int y = 0; y < initY; y++) {
                        int newValue = initialGrid[x][y] + nOffset - 1;
                        grid[xOffset + x][yOffset + y] = newValue % 9 + 1;
                    }
                }
            }
        }

        println("sizeX " + sizeX + ", sizeY " + sizeY);

        Graph graph = buildGraph(grid);
        getShortestPath(graph.getNode(0, 0));
        int answer = graph.getNode(sizeX - 1, sizeY - 1).risk;
        return "" + answer;
    }

    private Graph buildGraph(int[][] grid) {
        Graph graph = new Graph();
        int sizeX = grid[0].length;
        int sizeY = grid.length;

        for (int y = 0; y < sizeY; y++) {
            for (int x = 0; x < sizeX; x++) {
                Node node = new Node(x, y);
                graph.addNode(node);
            }
        }
        for (int y = 0; y < sizeY; y++) {
            for (int x = 0; x < sizeX; x++) {
                Node node = graph.getNode(x, y);

                for (var c : getPossibleCoords(new Coord(x, y), sizeX, sizeY)) {
                    node.addDestination(graph.getNode(c.x, c.y), grid[c.x][c.y]);
                }
                graph.addNode(node);
            }
        }
        return graph;
    }

    public void getShortestPath(Node start) {
        start.risk = 0;

        var settledNodes = new HashSet<Node>();
        var unsettledNodes = new HashSet<Node>();

        unsettledNodes.add(start);

        while (unsettledNodes.size() != 0) {
            Node currentNode = getLowestRiskNode(unsettledNodes);

            unsettledNodes.remove(currentNode);
            for (var adjacent : currentNode.adjacentNodes.entrySet()) {
                var adjacentNode = adjacent.getKey();
                var adjacentRisk = adjacent.getValue();
                if (!settledNodes.contains(adjacentNode)) {
                    calculateLowestRisk(adjacentNode, adjacentRisk, currentNode);
                    unsettledNodes.add(adjacentNode);
                }
            }
            settledNodes.add(currentNode);
        }
    }

    private void calculateLowestRisk(Node adjacentNode, Integer adjacentRisk, Node currentNode) {
        Integer currentRisk = currentNode.risk;
        if (currentRisk + adjacentRisk < adjacentNode.risk) {
            adjacentNode.risk = currentRisk + adjacentRisk;
//            var shortestPath = new LinkedList<Node>(currentNode.shortestPath);
//            shortestPath.add(currentNode);
//            adjacentNode.shortestPath = shortestPath;
        }
    }

    private Node getLowestRiskNode(Set<Node> unsettledNodes) {
        return unsettledNodes.stream().min(Comparator.comparingInt(n -> n.risk)).orElse(new Node(0, 0));
    }


    private List<Coord> getPossibleCoords(Coord coord, int sizeX, int sizeY) {
        List<Coord> possibleCoords = new ArrayList<>();
        if (coord.x > 0) {
            possibleCoords.add(new Coord(coord.x - 1, coord.y));
        }

        if (coord.x < sizeX - 1) {
            possibleCoords.add(new Coord(coord.x + 1, coord.y));
        }

        if (coord.y > 0) {
            possibleCoords.add(new Coord(coord.x, coord.y - 1));
        }

        if (coord.y < sizeY - 1) {
            possibleCoords.add(new Coord(coord.x, coord.y + 1));
        }
        return possibleCoords;
    }


    private int[][] parseInput() {
        int x = input.get(0).length();
        int y = input.size();

        int[][] inputGrid = new int[x][y];

        int iy = 0;
        for (var line : input) {

            for (int ix = 0; ix < x; ix++) {
                inputGrid[ix][iy] = Integer.parseInt(String.valueOf(line.charAt(ix)));
            }
            iy++;
        }
        return inputGrid;
    }


    private void printGrid(int[][] grid) {
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[0].length; x++) {
                print(grid[x][y] + "");
            }
            println("");
        }
    }

    public record Coord(int x, int y) {
    }

    public class Graph {

        public Map<String, Node> nodes = new HashMap<>();

        public Graph() {
        }

        public void addNode(Node node) {
            this.nodes.put(node.x + "," + node.y, node);
        }

        public Node getNode(int x, int y) {
            return nodes.get(x + "," + y);
        }

        @Override
        public String toString() {
            return "Graph{" +
                    "nodes=" + nodes +
                    '}';
        }
    }

    public class Node {
        public int x;
        public int y;
        public List<Node> shortestPath = new LinkedList<>();
        public Integer risk = Integer.MAX_VALUE;
        public Map<Node, Integer> adjacentNodes = new HashMap<>();


        public Node(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public void addDestination(Node destination, int distance) {
            this.adjacentNodes.put(destination, distance);
        }

        @Override
        public String toString() {

            StringBuilder path = new StringBuilder("[");
            for (var n : shortestPath) {
                path.append("(").append(n.x).append(",").append(n.y).append("), ");
            }
            path.append("]");

            StringBuilder adjNodes = new StringBuilder("[");
            for (var n : adjacentNodes.keySet()) {
                adjNodes.append("(").append(n.x).append(",").append(n.y).append("), ");
            }
            adjNodes.append("]");

            return "Node{" +
                    "x=" + x +
                    ", y=" + y +
                    ", risk=" + risk +
                    ", adjacentNodes=" + adjNodes +
                    ", shortestPath=" + path +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Node node = (Node) o;

            if (x != node.x) return false;
            return y == node.y;
        }

        @Override
        public int hashCode() {
            int result = x;
            result = 31 * result + y;
            return result;
        }
    }

}