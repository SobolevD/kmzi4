package org.example.key_generator;

import lombok.Builder;
import lombok.Data;

import java.math.BigInteger;
import java.util.List;

@Data
@Builder
public class PrivateKey {
    private List<BigInteger> pi;
    private BigInteger M;
    private BigInteger W;
    private List<BigInteger> sequence;
}
