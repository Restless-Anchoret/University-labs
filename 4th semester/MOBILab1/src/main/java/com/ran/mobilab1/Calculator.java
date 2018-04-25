package com.ran.mobilab1;

import java.math.BigInteger;

public class Calculator {

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
        BigInteger b = input.getB().mod(input.getN());
        return degreeBinary(a, b, input.getN()).toString();
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
        return "error";
    }
    
    public String quadraticResidue(Input input) {
        return "error";
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
}
