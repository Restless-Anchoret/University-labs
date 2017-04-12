package com.ran.dissertation.algebraic.quaternion;

import com.ran.dissertation.algebraic.common.AlgebraicObject;
import com.ran.dissertation.algebraic.common.ArithmeticOperations;
import com.ran.dissertation.algebraic.exception.AlgebraicException;
import com.ran.dissertation.algebraic.vector.ThreeDoubleVector;
import java.util.Objects;

public class Quaternion implements AlgebraicObject<Quaternion> {

    public static final Quaternion ZERO_QUATERNION = new Quaternion(0.0, ThreeDoubleVector.ZERO_THREE_DOUBLE_VECTOR);
    public static final Quaternion IDENTITY_QUANTERNION = new Quaternion(1.0, ThreeDoubleVector.ZERO_THREE_DOUBLE_VECTOR);
    
    public static Quaternion createForRotation(ThreeDoubleVector axis, double angle) {
        return new Quaternion(Math.cos(angle / 2.0), axis.normalized().multiply(Math.sin(angle / 2.0)));
    }
    
    public static Quaternion createFromVector(ThreeDoubleVector vector) {
        return new Quaternion(0.0, vector);
    }
    
    private final double scalar;
    private final ThreeDoubleVector vector;
    
    public Quaternion(double w, double x, double y, double z) {
        this(w, new ThreeDoubleVector(x, y, z));
    }
    
    public Quaternion(double scalar, ThreeDoubleVector vector) {
        this.scalar = scalar;
        this.vector = vector;
    }

    public double getScalar() {
        return scalar;
    }

    public ThreeDoubleVector getVector() {
        return vector;
    }
    
    public double getW() {
        return scalar;
    }
    
    public double getX() {
        return vector.getX();
    }
    
    public double getY() {
        return vector.getY();
    }
    
    public double getZ() {
        return vector.getZ();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + (int) (Double.doubleToLongBits(this.scalar) ^ (Double.doubleToLongBits(this.scalar) >>> 32));
        hash = 29 * hash + Objects.hashCode(this.vector);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Quaternion other = (Quaternion) obj;
        return (ArithmeticOperations.doubleEquals(this.scalar, other.scalar) &&
                Objects.equals(this.vector, other.vector));
    }
    
    @Override
    public Quaternion add(Quaternion other) {
        return new Quaternion(this.getScalar() + other.getScalar(), this.getVector().add(other.getVector()));
    }

    @Override
    public Quaternion substract(Quaternion other) {
        return new Quaternion(this.getScalar() - other.getScalar(), this.getVector().substract(other.getVector()));
    }

    @Override
    public Quaternion multiply(int number) {
        return multiply((double)number);
    }

    @Override
    public Quaternion multiply(double number) {
        return new Quaternion(this.getScalar() * number, this.getVector().multiply(number));
    }

    @Override
    public Quaternion multiply(Quaternion other) {
        return new Quaternion(this.getScalar() * other.getScalar() -
                this.getVector().scalarMultiply(other.getVector()),
                this.getVector().multiply(other.getScalar())
                    .add(other.getVector().multiply(this.getScalar()))
                    .add(this.getVector().multiply(other.getVector())));
    }

    @Override
    public Quaternion elementWiseMultiply(Quaternion other) {
        return new Quaternion(this.getScalar() * other.getScalar(),
                this.getVector().elementWiseMultiply(other.getVector()));
    }

    @Override
    public double scalarMultiply(Quaternion other) {
        return this.getScalar() * other.getScalar() + this.getVector().scalarMultiply(other.getVector());
    }
    
    public Quaternion getConjugate() {
        return new Quaternion(this.getScalar(), this.getVector().multiply(-1));
    }
    
    public double getQuaternionNorm() {
        return this.scalarMultiply(this);
    }
    
    public Quaternion getInverse() {
        if (this.isZero()) {
            throw new AlgebraicException("Inversing of zero quaternion");
        }
        return this.getConjugate().multiply(1.0 / this.getQuaternionNorm());
    }
    
    public Quaternion quaternionVectorMultiply(Quaternion middle, Quaternion right) {
        double resultScalar = this.getVector().mixedMultiply(middle.getVector(), right.getVector());
        ThreeDoubleVector resultVector = middle.getVector().multiply(right.getVector()).multiply(-this.getScalar())
                .add(right.getVector().multiply(this.getVector()).multiply(middle.getScalar()))
                .add(this.getVector().multiply(middle.getVector()).multiply(-right.getScalar()));
        return new Quaternion(resultScalar, resultVector);
    }
    
    public boolean isIdentity() {
        return ArithmeticOperations.doubleEquals(getQuaternionNorm(), 1.0);
    }

    @Override
    public String toString() {
        return "Quaternion{" + scalar + " + " +
                vector.getX() + "*i + " +
                vector.getY() + "*j + " +
                vector.getZ() + "*k}";
    }

}