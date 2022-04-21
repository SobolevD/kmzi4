package org.example.utils;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.math.BigInteger.ONE;
import static java.math.BigInteger.ZERO;

public class MathHelper {

    private static final Random RANDOM = new Random();

    public static BigInteger randomLongInRange(BigInteger leftBorder, BigInteger rightBorder) {
        return nextRandomBigInteger(rightBorder).add(ONE).add(leftBorder);
    }

    public static BigInteger getSequenceSum(List<BigInteger> sequence) {
        BigInteger result = ZERO;
        for (BigInteger elem : sequence) {
            result = result.add(elem);
        }
        return result;
    }

    public static List<BigInteger> generateSortedSequence(BigInteger n) {
        List<BigInteger> sortedSequence = new ArrayList<>();

        for (BigInteger i = ZERO; MathHelper.isLess(i, n); i = i.add(ONE)) {
            sortedSequence.add(i);
        }

        return sortedSequence;
    }

    public static boolean isBigger(BigInteger first, BigInteger second) {
        return !first.divide(second).equals(ZERO) && !first.equals(second);
    }

    public static boolean isLess(BigInteger first, BigInteger second) {
        return !isBigger(first, second);
    }

    public static BigInteger nextRandomBigInteger(BigInteger upperLimit) {
        BigInteger randomNumber;
        do {
            randomNumber = new BigInteger(upperLimit.bitLength(), RANDOM);
        } while (randomNumber.compareTo(upperLimit) >= 0);
        return randomNumber;
    }
}
