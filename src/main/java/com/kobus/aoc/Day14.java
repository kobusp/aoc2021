package com.kobus.aoc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Advent of Code 2021 Solutions
 * Day 14: Extended Polymerization
 *
 * @author Kobus Pretorius
 */
public class Day14 extends AoCRunnable {

    public static void main(String[] args) throws IOException {
        new Day14("14").run(true);
        new Day14("14").run(false);
    }

    public Day14(String dayNumber) {
        super(dayNumber);
        debugging = true;
    }

    public HashMap<String, HashMap<Integer, Map<String, Long>>> cacheAtDepth;

    @Override
    public String part1() {
        int answer;
        String template = input.get(0);
        HashMap<String, String> rules = new HashMap<>();
        for (var i = 2; i < input.size(); i++) {
            String[] parts = input.get(i).split(" ");
            rules.put(parts[0], parts[2]);
        }

        int iterations = 10;

        for (int i = 1; i < iterations + 1; i++) {
            template = apply(template, rules);
        }

        HashMap<String, Integer> counts = new HashMap<>();
        for (int i = 0; i < template.length(); i++) {
            String c = template.substring(i, i + 1);
            if (!counts.containsKey(c)) {
                counts.put(c, 1);
            } else {
                counts.put(c, counts.get(c) + 1);
            }
        }

        println("counts per letter: " + counts);

        List<Integer> sizes = counts.values().stream().sorted().toList();
        answer = sizes.get(sizes.size() - 1) - sizes.get(0);

        return "" + answer;
    }

    @Override
    public String part2() {
        cacheAtDepth = new HashMap<>();

        long answer;
        String template = input.get(0);

        HashMap<String, String> rules = new HashMap<>();
        for (var i = 2; i < input.size(); i++) {
            String[] parts = input.get(i).split(" ");
            rules.put(parts[0], parts[2]);
        }

        var tokenLookup = tokenizeRules(rules);
        var templateTokens = tokenizeTemplate(template);


        var countPerLetter = template.chars()
                .mapToObj(i -> (char) i).collect(Collectors.groupingBy(String::valueOf, Collectors.counting()));

        int iterations = 40;

        for (String token : templateTokens) {
            countPerLetter =
                    Stream.of(countPerLetter, countAddedLetters(token, tokenLookup, rules, 1, iterations))
                            .flatMap(map -> map.entrySet().stream())
                            .collect(Collectors.toMap(
                                    Map.Entry::getKey,
                                    Map.Entry::getValue,
                                    Long::sum
                            ));
        }

        println("countPerLetter: " + countPerLetter);

        List<Long> sizes = countPerLetter.values().stream().sorted().toList();
        answer = sizes.get(sizes.size() - 1) - sizes.get(0);

        return "" + answer;
    }

    private Map<String, Long> countAddedLetters(
            String token,
            HashMap<String, List<String>> tokenLookup,
            HashMap<String, String> rules,
            int depth,
            int maxDepth) {

        Map<String, Long> countPerLetter = new HashMap<>();
        countPerLetter.put(rules.get(token), 1L);

        if (depth == maxDepth) {
            return countPerLetter;
        } else {

            var childToken1 = tokenLookup.get(token).get(0);
            var childToken2 = tokenLookup.get(token).get(1);

            Map<String, Long> addedLetters1;
            Map<String, Long> addedLetters2;

            int remainingDepth = maxDepth - depth;

            if (cacheAtDepth.containsKey(childToken1) && cacheAtDepth.get(childToken1).containsKey(remainingDepth)) {
                addedLetters1 = cacheAtDepth.get(childToken1).get(remainingDepth);
            } else {
                addedLetters1 = countAddedLetters(childToken1, tokenLookup, rules, depth + 1, maxDepth);
                if (!cacheAtDepth.containsKey(childToken1)) {
                    cacheAtDepth.put(childToken1, new HashMap<>());
                }
                cacheAtDepth.get(childToken1).put(remainingDepth, addedLetters1);
            }

            if (cacheAtDepth.containsKey(childToken2) && cacheAtDepth.get(childToken2).containsKey(remainingDepth)) {
                addedLetters2 = cacheAtDepth.get(childToken2).get(remainingDepth);
            } else {
                addedLetters2 = countAddedLetters(childToken2, tokenLookup, rules, depth + 1, maxDepth);
                if (!cacheAtDepth.containsKey(childToken2)) {
                    cacheAtDepth.put(childToken2, new HashMap<>());
                }
                cacheAtDepth.get(childToken2).put(remainingDepth, addedLetters2);
            }

            return Stream.of(countPerLetter, addedLetters1, addedLetters2)
                    .flatMap(map -> map.entrySet().stream())
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            Map.Entry::getValue,
                            Long::sum
                    ));
        }
    }

    private List<String> tokenizeTemplate(String template) {
        var tokens = new ArrayList<String>();
        for (int i = 0; i < template.length() - 1; i++) {
            tokens.add(template.substring(i, i + 2));
        }
        return tokens;
    }

    private HashMap<String, List<String>> tokenizeRules(HashMap<String, String> rules) {
        HashMap<String, List<String>> tokens = new HashMap<>();
        for (String rule : rules.keySet()) {
            var result = List.of(rule.charAt(0) + rules.get(rule),
                    rules.get(rule) + rule.charAt(1));
            tokens.put(rule, result);
        }

        return tokens;
    }

    private String apply(String template, HashMap<String, String> rules) {
        StringBuilder newTemplate = new StringBuilder();

        for (int i = 0; i < template.length() - 1; i++) {
            String takeTwo = template.substring(i, i + 2);
            if (rules.containsKey(takeTwo)) {
                newTemplate.append(takeTwo.charAt(0)).append(rules.get(takeTwo));
//                println("  - " + takeTwo + " -> " + takeTwo.charAt(0) + rules.get(takeTwo) + takeTwo.charAt(1));
            } else {
                newTemplate.append(takeTwo.charAt(0));
//                println("  x " + takeTwo);
            }
        }
        newTemplate.append(template.substring(template.length() - 1));

        return newTemplate.toString();
    }

}