package com.kobus.aoc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Advent of Code 2021 Solutions
 * Runnable class extended by each day's solution.
 *
 * @author Kobus Pretorius
 */
public abstract class AoCRunnable {

    List<String> input;
    List<Integer> inputAsInt = null;
    String dayNumber;
    boolean debugging = false;

    public AoCRunnable(String dayNumber) {
        this.dayNumber = dayNumber;
    }

    String part1() {
        return "";
    }

    String part2() {
        return "";
    }

    /**
     * Run part 1 and part 2
     *
     * @param testMode the test mode
     * @throws IOException the io exception
     */
    public void run(boolean testMode) throws IOException {
        run(testMode, 1);
    }

    /**
     * Run part 1 and part 2
     *
     * @param testMode the test mode
     * @throws IOException the io exception
     */
    public void run(boolean testMode, int numExecutions) throws IOException {
        if (numExecutions == 1) {
            println("-------------- Day " + dayNumber + (testMode ? " [Test Mode] " : " ------------") + "------------", true);
        } else {
            debugging = false;
            println("╟──────────────────────────────────────────────────────────────────╢", true);
        }
        setInput(Files.readAllLines(Path.of("src/main/resources/day" + dayNumber + (testMode ? "-test" : "") + ".txt")));
        run(1, numExecutions);
        run(2, numExecutions);
    }

    /**
     * Run.
     *
     * @param partNumber the part number, 1 or 2
     */
    private void run(int partNumber, int numExecutions) {
        String answer = "";

        long worstExecution = 0;

        Instant start = Instant.now();
        for (int i = 0; i < numExecutions; i++) {
            Instant _start = Instant.now();
            if (1 == partNumber) {
                answer = part1();
            } else {
                answer = part2();
            }
            worstExecution = Math.max(worstExecution, Duration.between(_start, Instant.now()).toNanos());
        }
        String duration = String.format("%.3f", (Duration.between(start, Instant.now()).toNanos() / 1000000.0) / numExecutions) + "ms";
        String worstDuration = String.format("%.3f", (worstExecution / 1000000.0)) + "ms";

        if (numExecutions == 1) {
            println("#" + partNumber + ": " + String.format("%16s %24s", answer, duration), true);
        } else {
            println("║ " + String.format("%3s", dayNumber) + "  " + String.format("%4s", "#" + partNumber) + " " + String.format("%16s %19s %19s", answer, duration, "(" + worstDuration + ") ║"), true);
        }
    }

    private void println(String s, boolean force) {
        if (force) {
            System.out.println(s);
        }
    }


    public void parseInputAsInt() {
        if (inputAsInt == null) {
            inputAsInt = getInputAsIntegers();
        }
    }

    public IntStream getInputAsIntegerStream() {
        return input.stream().mapToInt(s -> Integer.parseInt(s, 10));
    }

    public List<Integer> getInputAsIntegers() {
        return getInputAsIntegerStream().boxed().collect(Collectors.toList());
    }

    public void setInput(List<String> input) {
        this.input = input;
    }

    public void print(String s) {
        if (debugging) {
            System.out.print(s);
        }
    }

    public void println(String s) {
        if (debugging) {
            System.out.println(s);
        }
    }

    public List<List<Object>> parse(Class... types) {
        var parsedInput = new ArrayList<List<Object>>();
        for (var line : input) {
            String[] split = line.split(" ");
            var parsedLine = new ArrayList<>();
            int i = 0;
            for (var type : types) {
                if (type.equals(String.class)) {
                    parsedLine.add(split[i]);
                } else if (type.equals(Integer.class)) {
                    parsedLine.add(Integer.parseInt(split[i], 10));
                }
                i++;
            }
            parsedInput.add(parsedLine);
        }
        return parsedInput;
    }

    public int parseBinaryStr(String binaryString) {
        return Integer.parseInt(binaryString, 2);
    }

    public List<Integer> splitAndParseToInt(String ticket, String delim) {
        return Arrays.stream(ticket.split(delim))
                .mapToInt(Integer::parseInt)
                .boxed()
                .collect(Collectors.toList());
    }

    public List<String> getLinesAfter(List<String> lines, String searchString) {
        return lines.subList(lines.indexOf(searchString) + 1, lines.size());
    }

    public int parseInt(String s) {
        return Integer.parseInt(s, 10);
    }

    public int[] increaseCapacity(int[] intList, int n) {
        return concatArrays(intList, new int[n - intList.length]);
    }

    public int[] concatArrays(int[] part1, int[] part2) {
        int[] complete;
        complete = Arrays.copyOf(part1, part1.length + part2.length);
        System.arraycopy(part2, 0, complete, part1.length, part2.length);
        return complete;
    }

    public String sort(String s) {
        char[] charArray = s.toCharArray();
        Arrays.sort(charArray);
        return new String(charArray);
    }
}
