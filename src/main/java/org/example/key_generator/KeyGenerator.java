package org.example.key_generator;

import org.example.utils.MathHelper;
import org.example.utils.PermutationHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class KeyGenerator {


    private static final long MAX_SEQUENCE_DELAY = 20;
    private static final Random RANDOM = new Random();
    private final long M;
    private final long W;
    private final List<Long> SEQUENCE;
    private final List<Long> MIXED_ARRAY;

    public KeyGenerator(int n) {
        List<Long> sequence = generateRandomSequence(n);
        long currentDelay = Math.abs(RANDOM.nextLong()) % MAX_SEQUENCE_DELAY;
        long M = MathHelper.getSequenceSum(sequence) + currentDelay;
        long W = generateRandomW(M);

        List<Long> mixedArray = PermutationHelper.makePermutation(MathHelper.generateSortedSequence(n));

        this.M = M;
        this.W = W;
        this.SEQUENCE = sequence;
        this.MIXED_ARRAY = mixedArray;

        System.out.printf("Sequence: %s, M = %d, W = %d, Mixed sequence: %s\n", sequence, M, W, mixedArray);
    }

    private List<Long> generateRandomSequence(int n) {
        List<Long> resultSequence = new ArrayList<>();

        for (int i = 0; i < n; ++i) {
            long currentDelay = Math.abs(RANDOM.nextLong()) % MAX_SEQUENCE_DELAY;
            long newNum = MathHelper.getSequenceSum(resultSequence) + currentDelay;
            resultSequence.add(newNum);
        }

        return resultSequence;
    }

    private long generateRandomW(long M) {
        long leftBorder = 1;
        long rightBorder = M - 1;
        while (true) {
            long probablyW = MathHelper.randomLongInRange(leftBorder, rightBorder);
            if (MathHelper.gcd(probablyW, M) == 1) {
                return probablyW;
            }
        }
    }

    public List<Long> generatePublicKey() {
        List<Long> publicKey = new ArrayList<>();

        for (Long position : MIXED_ARRAY) {
            int intPosition = position.intValue();
            publicKey.add((W * SEQUENCE.get(intPosition)) % M);
        }
        return publicKey;
    }

    public List<Long> generatePrivateKey() {

        List<Long> privateKey = new ArrayList<>(MIXED_ARRAY);
        privateKey.add(M);
        privateKey.add(W);
        privateKey.addAll(SEQUENCE);

        return privateKey;
    }
}
