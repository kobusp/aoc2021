package com.kobus.aoc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class AoCRunnable {

    List<String> input;
    List<Integer> inputAsInt = null;


    String part1() {
        return "";
    }


    String part2() {
        return "";
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
        System.out.print(s);
    }

    public void println(String s) {
        System.out.println(s);
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
}
