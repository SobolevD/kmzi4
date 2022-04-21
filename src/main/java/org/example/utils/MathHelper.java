package org.example.utils;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MathHelper {

    private static final Random RANDOM = new Random();

    public static long randomLongInRange(long leftBorder, long rightBorder) {
        return Math.abs(RANDOM.nextLong(rightBorder - leftBorder)) + leftBorder;
    }

    public static int randomIntInRange(int leftBorder, int rightBorder) {

        if (leftBorder == rightBorder)
            return leftBorder;

        return Math.abs(RANDOM.nextInt(rightBorder-leftBorder)) + leftBorder;
    }

    public static long gcd(long a, long b) {
        BigInteger aB = BigInteger.valueOf(a);
        BigInteger bB = BigInteger.valueOf(b);
        BigInteger gcd = aB.gcd(bB);
        return gcd.longValue();
    }

    public static long getSequenceSum(List<Long> sequence) {
        long result = 0;
        for (Long elem : sequence) {
            result += elem;
        }
        return result;
    }

    public static List<Long> generateSortedSequence(long n) {
        List<Long> sortedSequence = new ArrayList<>();

        for (long i = 0; i < n; ++i) {
            sortedSequence.add(i);
        }

        return sortedSequence;
    }
}
