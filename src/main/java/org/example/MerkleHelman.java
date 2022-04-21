package org.example;

import org.example.key_generator.KeyGenerator;

import java.util.List;

public class MerkleHelman {


    public long encryption(String m, int n) {
        KeyGenerator keyGenerator = new KeyGenerator(n);
        List<Long> a = keyGenerator.generatePublicKey();
        String textBinary = stringToBinary(m, n);
        long c = 0;
        for (int i = 0; i < n; i++) {
            c = +textBinary.charAt(i) * a.get(i);
        }
        return c;
    }

    public String decryption(long c){



        return null;
    }


    public String stringToBinary(String s, int n) {

        StringBuilder answer = new StringBuilder();

        for (int i = 0; i <= n; i++) {
            char c = s.charAt(i);

            answer.append(Integer.toBinaryString(c)).append(' ');
        }

        return answer.toString();
    }
}
