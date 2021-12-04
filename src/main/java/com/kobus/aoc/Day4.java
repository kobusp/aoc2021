package com.kobus.aoc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Advent of Code 2021 Solutions
 * Day 4
 *
 * @author Kobus Pretorius
 */
public class Day4 extends AoCRunnable {

    public static void main(String[] args) throws IOException {
        new Day4("4").run(true);
        new Day4("4").run(false);
    }

    public Day4(String dayNumber) {
        super(dayNumber);
        debugging = false;
    }

    @Override
    public String part1() {
        int answer = 0;
        List<Integer> calledNumbers = parseCalledNumbers(input.get(0));
        List<Board> boards = createBoards(input);

        for (var number : calledNumbers) {
            for (var board : boards) {
                board.callNumber(number);
                if (board.hasWon()) {
                    answer = board.sumOfRemainingNumbers() * number;
                    break;
                }
            }
            if (answer > 0) {
                break;
            }
        }

        return "" + answer;
    }

    @Override
    public String part2() {
        int answer = 0;

        List<Integer> calledNumbers = parseCalledNumbers(input.get(0));
        println(calledNumbers.toString());

        List<Board> boards = createBoards(input);

        for (var number : calledNumbers) {
            for (var board : boards) {
                board.callNumber(number);
                if (board.hasWon()) {
                    answer = board.sumOfRemainingNumbers() * number;
                }
            }
            // Remove winning boards
            boards = boards.stream().filter(board -> !board.hasWon()).collect(Collectors.toList());
            if (boards.size() == 1) {
                answer = boards.get(0).sumOfRemainingNumbers() * number;
            }
        }

        return "" + answer;
    }

    private List<Integer> parseCalledNumbers(String line) {
        return Arrays.stream(line.split(",")).mapToInt(this::parseInt).boxed().collect(Collectors.toList());
    }

    private List<Board> createBoards(List<String> input) {
        var boards = new ArrayList<Board>();
        int i = 1;
        while (i < input.size() && input.get(i).equals("")) {
            boards.add(new Board(input.subList(i + 1, i + 6)));
            i += 6;
        }
        return boards;
    }

    public class Board {

        public List<Integer> numbers;
        public List<Integer> numbersCalled;

        public Board(List<String> input) {
            input = input.stream().map(l -> l.trim().replaceAll(" +", " ")).collect(Collectors.toList());
            numbersCalled = new ArrayList<>();
            setNumbers(input);
        }

        public void setNumbers(List<String> input) {
            numbers = new ArrayList<>();
            for (var line : input) {
                numbers.addAll(Arrays.stream(line.split(" "))
                        .mapToInt(Day4.this::parseInt)
                        .boxed()
                        .collect(Collectors.toList()));
            }
        }

        public void callNumber(Integer n) {
            if (numbers.contains(n)) {
                numbers.set(numbers.indexOf(n), -1);
            }
        }

        public void print() {
            println(numbers.toString());
        }

        public boolean hasWon() {
            for (int x = 0; x < 5; x++) {
                int horizontalSum = 0;
                int verticalSum = 0;
                for (int y = 0; y < 5; y++) {
                    horizontalSum += numbers.get(x + (y * 5));
                    verticalSum += numbers.get(y + (x * 5));
                }
                if (horizontalSum == -5 || verticalSum == -5) {
                    return true;
                }
            }
            return false;
        }

        public int sumOfRemainingNumbers() {
            return numbers.stream().filter(i -> i >= 0).mapToInt(Integer::intValue).sum();
        }
    }
}