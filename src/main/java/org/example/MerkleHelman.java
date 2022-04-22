package org.example;

import org.example.key_generator.KeyGenerator;
import org.example.key_generator.PrivateKey;
import org.example.utils.MathHelper;
import org.example.utils.MyBitSet;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import static java.math.BigInteger.*;

public class MerkleHelman {

    public BigInteger encrypt(String input, List<BigInteger> publicKey) {

        String message = stringToBinary(input);

        BigInteger n = BigInteger.valueOf((long) input.length() * 8);
        if (MathHelper.isBigger(n, valueOf(publicKey.size()))) {
            throw new RuntimeException(String.format("Can't encrypt messages with length > %d with this key",
                    publicKey.size()));
        }

        BigInteger c = BigInteger.ZERO;
        for (int i = 0; i < message.length(); i++) {
            c = c.add(publicKey.get(i).multiply(new BigInteger(message.substring(i, i+1))));
        }

        return c;
    }

    public String decrypt(BigInteger c, PrivateKey privateKey){
        BigInteger W = privateKey.getW();
        BigInteger M = privateKey.getM();

        // Mod inverse = (W^-1 mod M)
        BigInteger d = c.mod(M).multiply(W.modInverse(M)).mod(M);
        System.out.println(d);

        List<BigInteger> pi = privateKey.getPi();

        List<BigInteger> sequence = privateKey.getSequence();

        byte[] decrypted_binary = new byte[sequence.size()];
        for (int i = sequence.size() - 1; i >= 0; i--) {
            if (sequence.get(i).compareTo(d) <= 0) {
                d = d.subtract(sequence.get(i));
                decrypted_binary[i] = 1;
            } else {
                decrypted_binary[i] = 0;
            }
        }

        byte[] result = new byte[sequence.size()];
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < result.length ; i++) {
            sb.append(decrypted_binary[pi.get(i).intValue()]);
        }

        return new String(new BigInteger(sb.toString(), 2).toByteArray());
    }

    public static void main(String[] args) {
        String message = "-";
        BigInteger n = valueOf(message.length()).multiply(valueOf(8L));
        MerkleHelman merkleHelman = new MerkleHelman();
        KeyGenerator keyGenerator = new KeyGenerator(n);
        List<BigInteger> publicKey = keyGenerator.generatePublicKey();
        BigInteger encrypt = merkleHelman.encrypt(message, publicKey);
        System.out.println(encrypt);
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
            while (binarySymbol.length() < 8) {
                binarySymbol.insert(0, '0');
            }
            answer.append(binarySymbol);
        }

        return answer.toString();
    }
}
