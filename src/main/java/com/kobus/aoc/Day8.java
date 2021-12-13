package com.kobus.aoc;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Advent of Code 2021 Solutions
 * Day 8: Seven Segment Search
 *
 * @author Kobus Pretorius
 */
public class Day8 extends AoCRunnable {

    public static void main(String[] args) throws IOException {
        new Day8("8").run(true);
        new Day8("8").run(false);
    }

    public Day8(String dayNumber) {
        super(dayNumber);
        debugging = true;
    }

    @Override
    public String part1() {
        int count = 0;
        var lengths = List.of(2, 3, 4, 7);
        for (String line : input) {
            count += Arrays.stream(line.split("\\|")[1].trim().split(" "))
                    .mapToInt(String::length)
                    .boxed()
                    .filter(lengths::contains)
                    .count();
        }

        return "" + count;
    }

    @Override
    public String part2() {
        return "" + input.stream().mapToInt(this::decode).sum();
    }

    private int decode(String line) {
        var parts = line.split("\\|");
        var digits = sort(parts[0].trim().split(" "), true);
        var output = sort(parts[1].trim().split(" "), false);

        List<String> digitsOfLength5 = getDigitsOfLength(digits, 5);
        List<String> digitsOfLength6 = getDigitsOfLength(digits, 6);

        var one = digits[0];
        var seven = digits[1];
        var four = digits[2];
        var eight = digits[9];
        var three = digitsOfLength5.stream().filter(s -> containsAllOf(s, one)).collect(Collectors.toList()).get(0);
        digitsOfLength5.remove(three);
        var five = digitsOfLength5.stream().filter(s -> containsAllOf(s, removeChars(four, one))).collect(Collectors.toList()).get(0);
        digitsOfLength5.remove(five);
        var two = digitsOfLength5.get(0);
        var nine = digitsOfLength6.stream().filter(s -> containsAllOf(s, one) && containsAllOf(s, four)).collect(Collectors.toList()).get(0);
        digitsOfLength6.remove(nine);
        var six = digitsOfLength6.stream().filter(s -> containsAllOf(s, five)).collect(Collectors.toList()).get(0);
        digitsOfLength6.remove(six);
        var zero = digitsOfLength6.get(0);

        var digitPatterns = new HashMap<String, String>();
        digitPatterns.put(zero, "0");
        digitPatterns.put(one, "1");
        digitPatterns.put(two, "2");
        digitPatterns.put(three, "3");
        digitPatterns.put(four, "4");
        digitPatterns.put(five, "5");
        digitPatterns.put(six, "6");
        digitPatterns.put(seven, "7");
        digitPatterns.put(eight, "8");
        digitPatterns.put(nine, "9");

        return Integer.parseInt(Arrays.stream(output)
                .map(digitPatterns::get)
                .collect(Collectors.joining()));
    }

    private String removeChars(String stringToChange, String charsToRemove) {
        for (var c : charsToRemove.toCharArray()) {
            stringToChange = stringToChange.replace(String.valueOf(c), "");
        }
        return stringToChange;
    }

    private boolean containsAllOf(String stringToCheck, String charsToMatch) {
        for (var c : charsToMatch.toCharArray()) {
            if (!stringToCheck.contains(String.valueOf(c))) {
                return false;
            }
        }
        return true;
    }

    private List<String> getDigitsOfLength(String[] digits, int n) {
        return Arrays.stream(digits).filter(s -> s.length() == n).collect(Collectors.toList());
    }

    private String[] sort(String[] strings, boolean finalSort) {
        List<String> sorted = new ArrayList<>();
        for (var s : strings) {
            sorted.add(sort(s));
        }
        if (finalSort) {
            sorted.sort(Comparator.comparingInt(String::length));
        }

        String[] sortedStrings = new String[strings.length];
        return sorted.toArray(sortedStrings);
    }
}