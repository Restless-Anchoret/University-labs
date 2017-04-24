package com.ran.dissertation.world;

import com.ran.dissertation.algebraic.exception.CreationException;
import com.ran.dissertation.algebraic.quaternion.Quaternion;
import com.ran.dissertation.algebraic.vector.ThreeDoubleVector;

public class Orientation {

    private static final ThreeDoubleVector DEFAULT_SCALE_REFLECTION_VECTOR = new ThreeDoubleVector(1.0, 1.0, 1.0);
    
    public static final Orientation INITIAL_ORIENTATION =
            new Orientation(ThreeDoubleVector.ZERO_THREE_DOUBLE_VECTOR, Quaternion.IDENTITY_QUANTERNION);
    
    public static Orientation createForOffset(ThreeDoubleVector offset) {
        return new Orientation(offset, Quaternion.IDENTITY_QUANTERNION);
    }
    
    public static Orientation createForOffset(double x, double y, double z) {
        return new Orientation(new ThreeDoubleVector(x, y, z), Quaternion.IDENTITY_QUANTERNION);
    }
    
    public static Orientation createForRotation(ThreeDoubleVector axis, double angle) {
        return createForOffsetAndRotation(ThreeDoubleVector.ZERO_THREE_DOUBLE_VECTOR, axis, angle);
    }
    
    public static Orientation createForOffsetAndRotation(ThreeDoubleVector offset, ThreeDoubleVector axis, double angle) {
        return new Orientation(offset, Quaternion.createForRotation(axis, angle));
    }
    
    public static Orientation createForOffsetAndRotation(double offsetX, double offsetY, double offsetZ,
            ThreeDoubleVector axis, double angle) {
        return createForOffsetAndRotation(new ThreeDoubleVector(offsetX, offsetY, offsetZ), axis, angle);
    }
    
    private final ThreeDoubleVector offset;
    private final Quaternion rotation;
    private final Quaternion conjugateRotation;
    private final ThreeDoubleVector scaleReflectionVector;

    public Orientation(ThreeDoubleVector offset, Quaternion rotation) {
        this(offset, rotation, DEFAULT_SCALE_REFLECTION_VECTOR);
    }

    public Orientation(ThreeDoubleVector offset, Quaternion rotation, ThreeDoubleVector scaleReflectionVector) {
        if (!rotation.isIdentity()) {
            throw new CreationException("Quaternion which represents orientation must be identity");
        }
        this.offset = offset;
        this.rotation = rotation;
        this.conjugateRotation = rotation.getConjugate();
        this.scaleReflectionVector = scaleReflectionVector;
    }

    private Orientation(ThreeDoubleVector offset, Quaternion rotation, Quaternion conjugateRotation,
            ThreeDoubleVector scaleReflectionVector) {
        this.offset = offset;
        this.rotation = rotation;
        this.conjugateRotation = conjugateRotation;
        this.scaleReflectionVector = scaleReflectionVector;
    }

    public ThreeDoubleVector getOffset() {
        return offset;
    }

    public Quaternion getRotation() {
        return rotation;
    }

    public Quaternion getConjugateRotation() {
        return conjugateRotation;
    }

    public ThreeDoubleVector getScaleReflectionVector() {
        return scaleReflectionVector;
    }
    
    public Orientation makeOrientationWithNewOffset(ThreeDoubleVector newOffset) {
        return new Orientation(newOffset, rotation, conjugateRotation, scaleReflectionVector);
    }
    
    public Orientation makeOrientationWithNewRotation(Quaternion newRotation) {
        return new Orientation(offset, newRotation, scaleReflectionVector);
    }
    
    public Orientation makeOrientationWithNewScaleReflectionVector(ThreeDoubleVector newScaleReflectionVector) {
        return new Orientation(offset, rotation, conjugateRotation, newScaleReflectionVector);
    }

    @Override
    public String toString() {
        return "Orientation{" + "offset=" + offset + ", rotation=" + rotation + '}';
    }
    
}