package com.kobus.aoc;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Advent of Code 2021 Solutions
 * Day 16
 *
 * @author Kobus Pretorius
 */
public class Day16 extends AoCRunnable {

    public static void main(String[] args) throws IOException {
        new Day16("16").run(true);
        new Day16("16").run(false);
    }

    public Day16(String dayNumber) {
        super(dayNumber);
        debugging = true;
    }

    @Override
    public String part1() {
        var packet = new Packet(parseInput(input.get(0)));
        return "" + packet.versionSum;
    }


    @Override
    public String part2() {
        var packet = new Packet(parseInput(input.get(0)));
        return "" + packet.evaluate();
    }

    private String parseInput(String line) {
        String s = new BigInteger(line, 16).toString(2);
        int expectedLength = line.length() * 4;
        String zeros = IntStream.range(0, expectedLength - s.length()).mapToObj(i -> "0").collect(Collectors.joining());
        return zeros + s;
    }

    public class Packet {
        public String binary;
        public int version;
        public int typeId;
        public long literalValue;
        boolean isLiteral;
        public int lengthTypeId; // for operator packets only
        public int lengthOfSubPackets;
        public int numOfSubPackets;
        public List<Packet> subPackets;
        public int totalLength;
        public int versionSum;

        public Packet(String binary) {
            this.binary = binary;
            subPackets = new ArrayList<>();
            // Determine the version and typeId
            version = Integer.parseInt(binary.substring(0, 3), 2);
            typeId = Integer.parseInt(binary.substring(3, 6), 2);
            isLiteral = typeId == 4;

            int cursor = 6;
            if (isLiteral) {
                StringBuilder binaryNumber = new StringBuilder();
                while (binary.charAt(cursor) == '1') {
                    binaryNumber.append(binary, cursor + 1, cursor + 5);
                    cursor += 5;
                }
                // The last group starts with 0
                binaryNumber.append(binary, cursor + 1, cursor + 5);
                literalValue = Long.parseLong(binaryNumber.toString(), 2);
                totalLength = cursor + 5;
                versionSum = version;
            } else {
                // is operator packet
                lengthTypeId = Integer.parseInt(binary.substring(cursor, cursor + 1), 2);
                cursor++;

                if (lengthTypeId == 0) {
                    lengthOfSubPackets = Integer.parseInt(binary.substring(cursor, cursor + 15), 2);
                    cursor += 15;
                    subPackets = createSubPacketsByLength(binary.substring(cursor, cursor + lengthOfSubPackets));
                } else {
                    numOfSubPackets = Integer.parseInt(binary.substring(cursor, cursor + 11), 2);
                    cursor += 11;
                    subPackets = createSubPacketsByNumber(binary.substring(cursor), numOfSubPackets);
                }

                totalLength = cursor + subPackets.stream().mapToInt(sp -> sp.totalLength).sum();
                versionSum = version + subPackets.stream().mapToInt(sp -> sp.versionSum).sum();
            }
        }

        private List<Packet> createSubPacketsByLength(String subPacketBinary) {
            List<Packet> packets = new ArrayList<>();
            while (subPacketBinary.length() > 0) {
                Packet subPacket = new Packet(subPacketBinary);
                packets.add(subPacket);
                subPacketBinary = subPacketBinary.substring(subPacket.totalLength);
            }
            return packets;
        }

        private List<Packet> createSubPacketsByNumber(String subPacketBinary, int numOfSubPackets) {
            List<Packet> packets = new ArrayList<>();
            for (int i = 0; i < numOfSubPackets; i++) {
                Packet subPacket = new Packet(subPacketBinary);
                packets.add(subPacket);
                subPacketBinary = subPacketBinary.substring(subPacket.totalLength);
            }
            return packets;
        }

        public long evaluate() {
            return switch (typeId) {
                case 0 -> // Sum
                        subPackets.stream().mapToLong(Packet::evaluate).sum();
                case 1 -> // Product
                        subPackets.stream().mapToLong(Packet::evaluate).reduce((a, b) -> (a * b)).orElse(0);
                case 2 -> // Min
                        subPackets.stream().mapToLong(Packet::evaluate).min().orElse(0);
                case 3 -> // Max
                        subPackets.stream().mapToLong(Packet::evaluate).max().orElse(0);
                case 4 -> literalValue;
                case 5 -> // Greater than
                        subPackets.get(0).evaluate() > subPackets.get(1).evaluate() ? 1 : 0;
                case 6 -> // Less than
                        subPackets.get(0).evaluate() < subPackets.get(1).evaluate() ? 1 : 0;
                case 7 -> // Equal to
                        subPackets.get(0).evaluate() == subPackets.get(1).evaluate() ? 1 : 0;
                default -> 0;
            };
        }

        @Override
        public String toString() {
            return "Packet{" +
                    "binary='" + binary + '\'' +
                    "\n, version=" + version +
                    ", typeId=" + (typeId == 4 ? "LITERAL" : "OPERATOR") +
                    ", literalValue=" + literalValue +
                    ", isLiteral=" + isLiteral +
                    ", lengthTypeId=" + lengthTypeId +
                    ", lengthOfSubPackets=" + lengthOfSubPackets +
                    ", numSubPackets=" + numOfSubPackets +
                    ", totalLength=" + totalLength +
                    ", versionSum=" + versionSum +
                    "\n, subPackets=" + subPackets +
                    '}';
        }
    }
}