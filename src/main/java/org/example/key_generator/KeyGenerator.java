package org.example.key_generator;

import org.example.utils.MathHelper;
import org.example.utils.PermutationHelper;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.math.BigInteger.*;

public class KeyGenerator {


    private static final long MAX_SEQUENCE_DELAY = 20;
    private static final Random RANDOM = new Random();
    private final BigInteger M;
    private final BigInteger W;
    private final List<BigInteger> SEQUENCE;
    private final List<BigInteger> MIXED_ARRAY;

    public KeyGenerator(BigInteger n) {
        List<BigInteger> sequence = generateRandomSequence(n);
        BigInteger currentDelay = BigInteger.valueOf(Math.abs(RANDOM.nextLong()) % MAX_SEQUENCE_DELAY);
        BigInteger M = MathHelper.getSequenceSum(sequence).add(currentDelay);
        BigInteger W = generateRandomW(M);

        List<BigInteger> mixedArray = PermutationHelper.makePermutation(MathHelper.generateSortedSequence(n));

        this.M = M;
        this.W = W;
        this.SEQUENCE = sequence;
        this.MIXED_ARRAY = mixedArray;

        System.out.printf("Sequence: %s, M = %d, W = %d, Mixed sequence: %s\n", sequence, M, W, mixedArray);
    }

    private List<BigInteger> generateRandomSequence(BigInteger n) {
        List<BigInteger> resultSequence = new ArrayList<>();

        for (BigInteger i = ZERO; MathHelper.isLess(i, n.subtract(ONE)); i = i.add(ONE)) {
            long currentDelay = Math.abs(RANDOM.nextLong()) % MAX_SEQUENCE_DELAY;
            BigInteger newNum = MathHelper.getSequenceSum(resultSequence).add(BigInteger.valueOf(currentDelay));
            resultSequence.add(newNum);
        }

        return resultSequence;
    }

    private BigInteger generateRandomW(BigInteger M) {
        BigInteger rightBorder = M.subtract(ONE);
        while (true) {
            BigInteger probablyW = MathHelper.randomLongInRange(ONE, rightBorder);
            if (probablyW.gcd(M).equals(ONE)) {
                return probablyW;
            }
        }
    }

    public List<BigInteger> generatePublicKey() {
        List<BigInteger> publicKey = new ArrayList<>();

        for (BigInteger position : MIXED_ARRAY) {
            int intPosition = position.intValue();
            publicKey.add((W.multiply(SEQUENCE.get(intPosition))).remainder(M));
        }
        return publicKey;
    }

    public PrivateKey generatePrivateKey() {

        PrivateKey key = PrivateKey.builder()
                .pi(MIXED_ARRAY)
                .M(M)
                .W(W)
                .sequence(SEQUENCE)
                .build();

        return key;
    }
}
