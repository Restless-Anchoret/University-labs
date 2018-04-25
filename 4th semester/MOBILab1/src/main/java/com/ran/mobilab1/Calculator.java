package com.ran.mobilab1;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class Calculator {
    
    private static final int PRIME_NUMBERS_BOUND = 10_000_000;
    
    private List<BigInteger> primeNumbers = new ArrayList<>();
    
    public void evaluatePrimeNumbers() {
        boolean[] isPrime = new boolean[PRIME_NUMBERS_BOUND + 1];
        Arrays.fill(isPrime, true);
        isPrime[0] = isPrime[1] = false;
        
        for (int i = 2; i <= PRIME_NUMBERS_BOUND; i++) {
            if (isPrime[i]) {
                long j = (long)i * (long)i;
                while (j <= PRIME_NUMBERS_BOUND) {
                    isPrime[(int)j] = false;
                    j += i;
                }
                primeNumbers.add(BigInteger.valueOf(i));
            }
        }
    }

    public String mod(Input input) {
        return input.getA().mod(input.getN()).toString();
    }
    
    public String equal(Input input) {
        BigInteger a = input.getA().mod(input.getN());
        BigInteger b = input.getB().mod(input.getN());
        return Boolean.toString(a.equals(b));
    }
    
    public String sum(Input input) {
        BigInteger a = input.getA().mod(input.getN());
        BigInteger b = input.getB().mod(input.getN());
        return a.add(b).mod(input.getN()).toString();
    }
    
    public String subtract(Input input) {
        BigInteger a = input.getA().mod(input.getN());
        BigInteger b = input.getB().mod(input.getN());
        return a.subtract(b).mod(input.getN()).toString();
    }
    
    public String degree(Input input) {
        BigInteger a = input.getA().mod(input.getN());
        return degreeBinary(a, input.getN(), input.getN()).toString();
    }
    
    public String sqrt(Input input) {
        return "error";
    }
    
    public String multiplication(Input input) {
        BigInteger a = input.getA().mod(input.getN());
        BigInteger b = input.getB().mod(input.getN());
        return a.multiply(b).mod(input.getN()).toString();
    }
    
    public String reverse(Input input) {
        BigInteger a = input.getA().mod(input.getN());
        Map<BigInteger, Long> aPrimeDivisors = getPrimeDivisors(input.getA());
        Map<BigInteger, Long> nPrimeDivisors = getPrimeDivisors(input.getN());
        if (containSameKey(aPrimeDivisors, nPrimeDivisors)) {
            return "error";
        }
        BigInteger eilerFunctionValue = getEilerFunctionValue(input.getN(), nPrimeDivisors);
        return degreeBinary(a, eilerFunctionValue.subtract(BigInteger.ONE), input.getN()).toString();
    }
    
    public String quadraticResidue(Input input) {
        Set<Long> residues = new TreeSet<>();
        for (long i = 1; i <= (input.getN().longValue() - 1) / 2; i++) {
            BigInteger value = BigInteger.valueOf(i);
            BigInteger square = value.multiply(value);
            residues.add(square.mod(input.getN()).longValue());
        }
        StringBuilder builder = new StringBuilder();
        residues.forEach(number -> {
            if (builder.length() > 0) {
                builder.append(", ");
            }
            builder.append(number);
        });
        return builder.toString();
    }
    
    private BigInteger getEilerFunctionValue(BigInteger n, Map<BigInteger, Long> primeDivisors) {
        if (primeDivisors == null) {
            primeDivisors = getPrimeDivisors(n);
        }
        System.out.println(primeDivisors);
        BigInteger result = BigInteger.ONE;
        for (Map.Entry<BigInteger, Long> entry: primeDivisors.entrySet()) {
            BigInteger value = degreeBinary(entry.getKey(), entry.getValue() - 1);
            result = result.multiply(value.multiply(entry.getKey()).subtract(value));
        }
        System.out.println(result);
        return result;
    }
    
    private BigInteger degreeBinary(BigInteger a, BigInteger b, BigInteger n) {
        if (b.equals(BigInteger.ZERO)) {
            return BigInteger.ONE;
        } else if (b.equals(BigInteger.ONE)) {
            return a;
        } else if (b.mod(BigInteger.valueOf(2)).equals(BigInteger.ZERO)) {
            BigInteger value = degreeBinary(a, b.divide(BigInteger.valueOf(2)), n);
            return value.multiply(value).mod(n);
        } else {
            BigInteger value = degreeBinary(a, b.subtract(BigInteger.ONE), n);
            return value.multiply(a).mod(n);
        }
    }
    
    private BigInteger degreeBinary(BigInteger a, long b) {
        if (b == 0) {
            return BigInteger.ONE;
        } else if (b == 1) {
            return a;
        } else if (b % 2 == 0) {
            BigInteger value = degreeBinary(a, b / 2);
            return value.multiply(value);
        } else {
            BigInteger value = degreeBinary(a, b - 1);
            return value.multiply(a);
        }
    }
    
    private Map<BigInteger, Long> getPrimeDivisors(BigInteger number) {
        BigInteger currentValue = number;
        Map<BigInteger, Long> primeDivisors = new HashMap<>();
        for (BigInteger divisor: primeNumbers) {
            BigInteger square = divisor.multiply(divisor);
            if (square.compareTo(number) > 0) {
                break;
            }
            while (currentValue.mod(divisor).equals(BigInteger.ZERO)) {
                currentValue = currentValue.divide(divisor);
                incrementValueInMap(primeDivisors, divisor);
            }
        }
        if (currentValue.compareTo(BigInteger.ONE) > 0) {
            incrementValueInMap(primeDivisors, currentValue);
        }
        return primeDivisors;
    }
    
    private void incrementValueInMap(Map<BigInteger, Long> map, BigInteger key) {
        if (map.containsKey(key)) {
            map.put(key, map.get(key) + 1);
        } else {
            map.put(key, 1L);
        }
    }
    
    private boolean containSameKey(Map<BigInteger, Long> a, Map<BigInteger, Long> b) {
        Set<BigInteger> aSet = a.keySet();
        Set<BigInteger> bSet = b.keySet();
        aSet.retainAll(bSet);
        return !aSet.isEmpty();
    }
}

class Input {
    private BigInteger a, b, n;

    public Input(BigInteger a, BigInteger b, BigInteger n) {
        this.a = a;
        this.b = b;
        this.n = n;
    }

    public BigInteger getA() {
        return a;
    }

    public void setA(BigInteger a) {
        this.a = a;
    }

    public BigInteger getB() {
        return b;
    }

    public void setB(BigInteger b) {
        this.b = b;
    }

    public BigInteger getN() {
        return n;
    }

    public void setN(BigInteger n) {
        this.n = n;
    }

    @Override
    public String toString() {
        return "Input{" + "a=" + a + ", b=" + b + ", n=" + n + '}';
    }
    
    public void validate() {
        if (n.compareTo(BigInteger.ZERO) <= 0 ||
                a.compareTo(BigInteger.ZERO) < 0 ||
                b.compareTo(BigInteger.ZERO) <= 0) {
            throw new RuntimeException("Invalid input!");
        }
    }
}
