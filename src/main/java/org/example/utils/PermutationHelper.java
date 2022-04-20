package org.example.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PermutationHelper {

    public static List<Long> makePermutation(List<Long> inputSequence) {

        List<Long> resultSequence = new ArrayList<>(inputSequence);
        Collections.fill(resultSequence, 0L);

        List<Integer> availablePositions = new ArrayList<>();

        for (int i = 0; i < inputSequence.size(); ++i) {
            availablePositions.add(i);
        }

        for (Long aLong : inputSequence) {
            int index = MathHelper.randomIntInRange(0, availablePositions.size());
            int randomPosition = availablePositions.get(index);
            availablePositions.remove(index);
            resultSequence.set(randomPosition, aLong);
        }

        return resultSequence;
    }
}
