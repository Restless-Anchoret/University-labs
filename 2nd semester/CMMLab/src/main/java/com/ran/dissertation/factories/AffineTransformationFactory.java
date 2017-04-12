package com.ran.dissertation.factories;

import com.ran.dissertation.algebraic.quaternion.Quaternion;
import com.ran.dissertation.algebraic.vector.ThreeDoubleVector;
import com.ran.dissertation.world.AffineTransformation;

public class AffineTransformationFactory {

    private static AffineTransformationFactory INSTANCE = new AffineTransformationFactory();
    
    public static AffineTransformationFactory getInstance() {
        return INSTANCE;
    }
    
    private AffineTransformationFactory() { }
    
    public AffineTransformation createIdenticalAffineTransformation() {
        return new AffineTransformation(orientation -> orientation);
    }
    
    public AffineTransformation createXAxisReflectionAffineTransformation() {
        return createScaleAffineTransformation(new ThreeDoubleVector(1.0, -1.0, -1.0));
    }
    
    public AffineTransformation createYAxisReflectionAffineTransformation() {
        return createScaleAffineTransformation(new ThreeDoubleVector(-1.0, 1.0, -1.0));
    }
    
    public AffineTransformation createZAxisReflectionAffineTransformation() {
        return createScaleAffineTransformation(new ThreeDoubleVector(-1.0, -1.0, 1.0));
    }
    
    public AffineTransformation createXOYReflectionAffineTransformation() {
        return createScaleAffineTransformation(new ThreeDoubleVector(1.0, 1.0, -1.0));
    }
    
    public AffineTransformation createXOZReflectionAffineTransformation() {
        return createScaleAffineTransformation(new ThreeDoubleVector(1.0, -1.0, 1.0));
    }
    
    public AffineTransformation createYOZReflectionAffineTransformation() {
        return createScaleAffineTransformation(new ThreeDoubleVector(-1.0, 1.0, 1.0));
    }
    
    public AffineTransformation createCenterReflectionAffineTransformation() {
        return createScaleAffineTransformation(new ThreeDoubleVector(-1.0, -1.0, -1.0));
    }
    
    public AffineTransformation createScaleAffineTransformation(double number) {
        return createScaleAffineTransformation(new ThreeDoubleVector(number, number, number));
    }
    
    private AffineTransformation createScaleAffineTransformation(ThreeDoubleVector scaleVector) {
        return new AffineTransformation(orientation -> orientation.makeOrientationWithNewScaleReflectionVector(
                orientation.getScaleReflectionVector().elementWiseMultiply(scaleVector)));
    }
    
    public AffineTransformation createOffsetAffineTransformation(ThreeDoubleVector offsetVector) {
        return new AffineTransformation(orientation -> orientation.makeOrientationWithNewOffset(
                orientation.getOffset().add(offsetVector)));
    }
    
    public AffineTransformation createXAxisRotationAffineTransformation(double angle) {
        return createRotationAffineTransformation(new ThreeDoubleVector(1.0, 0.0, 0.0), angle);
    }
    
    public AffineTransformation createYAxisRotationAffineTransformation(double angle) {
        return createRotationAffineTransformation(new ThreeDoubleVector(0.0, 1.0, 0.0), angle);
    }
    
    public AffineTransformation createZAxisRotationAffineTransformation(double angle) {
        return createRotationAffineTransformation(new ThreeDoubleVector(0.0, 0.0, 1.0), angle);
    }
    
    public AffineTransformation createRotationAffineTransformation(ThreeDoubleVector axis, double angle) {
        Quaternion rotation = Quaternion.createForRotation(axis, angle);
        return new AffineTransformation(orientation -> orientation.makeOrientationWithNewRotation(
                rotation.multiply(orientation.getRotation())));
    }
    
    public AffineTransformation createXAxisRotationOverPointAffineTransformation(ThreeDoubleVector point, double angle) {
        return createRotationOverPointAffineTransformation(point, new ThreeDoubleVector(1.0, 0.0, 0.0), angle);
    }
    
    public AffineTransformation createYAxisRotationOverPointAffineTransformation(ThreeDoubleVector point, double angle) {
        return createRotationOverPointAffineTransformation(point, new ThreeDoubleVector(0.0, 1.0, 0.0), angle);
    }
    
    public AffineTransformation createZAxisRotationOverPointAffineTransformation(ThreeDoubleVector point, double angle) {
        return createRotationOverPointAffineTransformation(point, new ThreeDoubleVector(0.0, 0.0, 1.0), angle);
    }
    
    public AffineTransformation createRotationOverPointAffineTransformation(
            ThreeDoubleVector point, ThreeDoubleVector axis, double angle) {
        Quaternion rotation = Quaternion.createForRotation(axis, angle);
        Quaternion rotationConjugate = rotation.getConjugate();
        return new AffineTransformation(orientation -> {
            ThreeDoubleVector newOffset = rotation
                    .multiply(Quaternion.createFromVector(orientation.getOffset().substract(point)))
                    .multiply(rotationConjugate)
                    .getVector().add(point);
            return orientation.makeOrientationWithNewRotation(rotation.multiply(orientation.getRotation()))
                    .makeOrientationWithNewOffset(newOffset);
        });
    }
    
}