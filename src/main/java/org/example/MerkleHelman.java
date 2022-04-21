package org.example;

import org.example.key_generator.KeyGenerator;
import org.example.key_generator.PrivateKey;
import org.example.utils.MathHelper;
import org.example.utils.MyBitSet;

import java.math.BigInteger;
import java.util.List;

import static java.math.BigInteger.ONE;
import static java.math.BigInteger.ZERO;

public class MerkleHelman {


    public BigInteger encrypt(String message, List<BigInteger> publicKey) {

        BigInteger n = BigInteger.valueOf((long) message.length() * Character.SIZE);

        if (MathHelper.isBigger(n, BigInteger.valueOf(publicKey.size()))) {
            throw new RuntimeException(String.format("Can't encrypt messages with length > %d with this key",
                    publicKey.size()));
        }

        String textBinary = stringToBinary(message);
        BigInteger c = ZERO;
        for (BigInteger i = ZERO; MathHelper.isLess(i, n.subtract(ONE)); i = i.add(ONE)) {
            c = c.add(publicKey.get(i.intValue()).multiply(BigInteger.valueOf(textBinary.charAt(i.intValue()))));
        }
        return c;
    }

    public String decrypt(BigInteger c, PrivateKey privateKey){
        BigInteger W = privateKey.getW();
        BigInteger M = privateKey.getM();

        // Mod inverse = (W^-1 mod M)
        BigInteger d = c.multiply(W.modInverse(M));

        MyBitSet bitSet = new MyBitSet();
        List<BigInteger> sequence = privateKey.getSequence();
        for (int i = sequence.size() - 1; i >= 0; i--) {
            if (sequence.get(i).compareTo(d) <= 0) {
                d = d.subtract(sequence.get(i));
                bitSet.set(i);
            }
        }
        return bitSet.toString();
    }

    public static void main(String[] args) {
        String message = "2";
        BigInteger n = BigInteger.valueOf(message.length()).multiply(BigInteger.valueOf(Character.SIZE));
        MerkleHelman merkleHelman = new MerkleHelman();
        KeyGenerator keyGenerator = new KeyGenerator(n);
        List<BigInteger> publicKey = keyGenerator.generatePublicKey();
        BigInteger encrypt = merkleHelman.encrypt(message, publicKey);

        // Decrypt
        PrivateKey privateKey = keyGenerator.generatePrivateKey();
        String result = merkleHelman.decrypt(encrypt, privateKey);

        System.out.println(encrypt);
        System.out.println(result);
    }


    private String stringToBinary(String string) {

        StringBuilder answer = new StringBuilder();

        for (int i = 0; i < string.length(); i++) {
            char c = string.charAt(i);
            StringBuilder binarySymbol = new StringBuilder(Integer.toBinaryString(c));
            while (binarySymbol.length() < 16) {
                binarySymbol.insert(0, '0');
            }
            answer.append(binarySymbol);
        }

        return answer.toString();
    }
}
