package org.example.utils;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PermutationHelper {

    public static List<BigInteger> makePermutation(List<BigInteger> inputSequence) {

        List<BigInteger> resultSequence = new ArrayList<>(inputSequence);
        Collections.fill(resultSequence, BigInteger.ZERO);

        List<Integer> availablePositions = new ArrayList<>();

        for (int i = 0; i < inputSequence.size(); ++i) {
            availablePositions.add(i);
        }

        for (BigInteger aLong : inputSequence) {
            BigInteger index = MathHelper.nextRandomBigInteger(BigInteger.valueOf(availablePositions.size()));
            int randomPosition = availablePositions.get(index.intValue());
            availablePositions.remove(index.intValue());
            resultSequence.set(randomPosition, aLong);
        }

        return resultSequence;
    }
}
