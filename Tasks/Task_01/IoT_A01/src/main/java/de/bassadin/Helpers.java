package de.bassadin;

import java.util.Random;

public class Helpers {

    public static void printLogWithPrefix(String prefix, String message) {
        System.out.println(prefix + " - " + message);
    }

    public static long randomIntBetween(long low, long high) {
        Random r = new Random();
        return r.nextLong(high + 1 - low) + low;
    }

    // https://stackoverflow.com/a/29996205
    public static long calculateDiffieHellmanFormula(long base, long power, long prime) {
        long result = base;

        for (int i = 1; i < power; ++i) {
            result = result * base % prime;

        }
        return result;
    }

    public static double randomFloatBetween(double min, double max) {
        return min + Math.random() * (max - min);
    }
}
