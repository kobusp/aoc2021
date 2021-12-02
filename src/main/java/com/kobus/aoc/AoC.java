package com.kobus.aoc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;

public class AoC {

    public AoC() {

    }

    /**
     * Run part 1 and part 2
     *
     * @param runnable
     * @param day
     * @param testMode
     * @throws IOException
     */
    public void run(AoCRunnable runnable, String day, boolean testMode) throws IOException {
        print("=== Day " + day + " ====");
        run(runnable, day, 1, testMode);
        run(runnable, day, 2, testMode);
    }

    public void run(AoCRunnable runnable, String day, int partNumber, boolean testMode) throws IOException {
        print("--- Part " + partNumber + " ---");

        String answer;

        runnable.setInput(Files.readAllLines(Path.of("src/main/resources/day" + day + (testMode ? "-test" : "") + ".txt")));
        Instant start = Instant.now();
        if (1 == partNumber) {
            answer = runnable.part1();
        } else {
            answer = runnable.part2();
        }
        Instant end = Instant.now();

        print("Answer = " + answer);
        print("Execution time part " + partNumber + ": " + Duration.between(start, end).toMillis() + "ms");
    }

    public static void print(String s) {
        System.out.println(s);
    }

}
