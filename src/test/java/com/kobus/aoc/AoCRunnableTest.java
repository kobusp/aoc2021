package com.kobus.aoc;

import org.junit.jupiter.api.Test;


import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class AoCRunnableTest {

    private AoCRunnable runnable;

    @Test
    public void parse_should_parse_correctly() {
        runnable = new AoCRunnable("") {
            @Override
            String part1() {
                return super.part1();
            }
        };
        var input = List.of("up 1", "down 2", "left 3", "right 4");
        runnable.setInput(input);
        runnable.println("number of lines = " + input.size());
        var parsed = runnable.parse(String.class, Integer.class);
        runnable.println("number of parsed lines = " + parsed.size());

        parsed.forEach(l -> runnable.println(l.get(0) + "," + l.get(1)));

        int total = parsed.stream().mapToInt(l -> (Integer) l.get(1)).sum();
        runnable.println("Total = " + total);
        assertThat(total).isEqualTo(10);
    }

}