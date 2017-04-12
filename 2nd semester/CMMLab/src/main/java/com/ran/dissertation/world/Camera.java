package com.ran.dissertation.world;

import com.ran.dissertation.algebraic.matrix.DoubleMatrix;
import com.ran.dissertation.algebraic.vector.ThreeDoubleVector;

public class Camera {

    private static final double ANGLE_Z_UP_EDGE = 23.0 * Math.PI / 48.0;
    
    private static final double DEFAULT_ANGLE_XOY = 0.0;
    private static final double DEFAULT_ANGLE_Z = 0.0;
    private static final ThreeDoubleVector DEFAULT_POSITION = new ThreeDoubleVector(0.0, 6.0, 4.0);
    private static final ThreeDoubleVector DEFAULT_VERTICAL_VECTOR = new ThreeDoubleVector(0.0, 0.0, 1.0);
    private static final double DEFAULT_REVERSED_DISTANCE_BEHIND = 0.25;
    private static final double DEFAULT_LENS_WIDTH = 8.0;
    
    public static Camera createForPositionAndAngles(ThreeDoubleVector position,
            double angleXOY, double angleZ) {
        return new Camera(angleXOY, angleZ, position, DEFAULT_VERTICAL_VECTOR,
                DEFAULT_REVERSED_DISTANCE_BEHIND, DEFAULT_LENS_WIDTH);
    }
    
    public static Camera createForPositionReversedDistanceAndLensWidth(
            ThreeDoubleVector position, double reversedDistanceBehind, double lensWidth) {
        return new Camera(DEFAULT_ANGLE_XOY, DEFAULT_ANGLE_Z, position, DEFAULT_VERTICAL_VECTOR,
                reversedDistanceBehind, lensWidth);
    }
    
    private double angleXOY;
    private double angleZ;
    private ThreeDoubleVector position;
    private ThreeDoubleVector normVector;
    private ThreeDoubleVector verticalVector;
    private double reversedDistanceBehind;
    private double lensWidth;
    private DoubleMatrix worldToViewCoordinatesConvertMatrix = null;
    private DoubleMatrix viewToProjectionCoordinatesConvertMatrix = null;
    
    public Camera(double angleXOY, double angleZ, ThreeDoubleVector position,
            ThreeDoubleVector verticalVector, double reversedDistanceBehind, double lensWidth) {
        this.angleXOY = angleXOY;
        this.angleZ = angleZ;
        this.position = position;
        this.verticalVector = verticalVector;
        this.reversedDistanceBehind = reversedDistanceBehind;
        this.lensWidth = lensWidth;
        updateNormVector();
    }

    public Camera() {
        this(DEFAULT_ANGLE_XOY, DEFAULT_ANGLE_Z, DEFAULT_POSITION, DEFAULT_VERTICAL_VECTOR,
                DEFAULT_REVERSED_DISTANCE_BEHIND, DEFAULT_LENS_WIDTH);
    }

    public double getAngleXOY() {
        return angleXOY;
    }

    public double getAngleZ() {
        return angleZ;
    }

    public ThreeDoubleVector getPosition() {
        return position;
    }

    public ThreeDoubleVector getNormVector() {
        return normVector;
    }

    public ThreeDoubleVector getVerticalVector() {
        return verticalVector;
    }

    public double getReversedDistanceBehind() {
        return reversedDistanceBehind;
    }

    public double getLensWidth() {
        return lensWidth;
    }

    public void setLensWidth(double lensWidth) {
        this.lensWidth = lensWidth;
    }

    public DoubleMatrix getWorldToViewCoordinatesConvertMatrix() {
        return worldToViewCoordinatesConvertMatrix;
    }

    public void setWorldToViewCoordinatesConvertMatrix(DoubleMatrix worldToViewCoordinatesConvertMatrix) {
        this.worldToViewCoordinatesConvertMatrix = worldToViewCoordinatesConvertMatrix;
    }

    public DoubleMatrix getViewToProjectionCoordinatesConvertMatrix() {
        return viewToProjectionCoordinatesConvertMatrix;
    }

    public void setViewToProjectionCoordinatesConvertMatrix(DoubleMatrix viewToProjectionCoordinatesConvertMatrix) {
        this.viewToProjectionCoordinatesConvertMatrix = viewToProjectionCoordinatesConvertMatrix;
    }

    public void changeAngleXOY(double angle) {
        angleXOY += angle;
        updateNormVector();
    }

    public void changeAngleZ(double angle) {
        if (-ANGLE_Z_UP_EDGE <= angleZ + angle && angleZ + angle <= ANGLE_Z_UP_EDGE) {
            angleZ += angle;
            updateNormVector();
        }
    }

    public void moveX(double step) {
        position = new ThreeDoubleVector(position.getX() + step, position.getY(), position.getZ());
        worldToViewCoordinatesConvertMatrix = null;
    }

    public void moveY(double step) {
        position = new ThreeDoubleVector(position.getX(), position.getY() + step, position.getZ());
        worldToViewCoordinatesConvertMatrix = null;
    }

    public void moveZ(double step) {
        position = new ThreeDoubleVector(position.getX(), position.getY(), position.getZ() + step);
        worldToViewCoordinatesConvertMatrix = null;
    }
    
    public void moveRight(double step) {
        position = new ThreeDoubleVector(position.getX() + step * Math.cos(-angleXOY),
                position.getY() + step * Math.sin(-angleXOY), position.getZ());
        worldToViewCoordinatesConvertMatrix = null;
    }
    
    public void moveLeft(double step) {
        position = new ThreeDoubleVector(position.getX() - step * Math.cos(-angleXOY),
                position.getY() - step * Math.sin(-angleXOY), position.getZ());
        worldToViewCoordinatesConvertMatrix = null;
    }
    
    public void moveForward(double step) {
        position = new ThreeDoubleVector(position.getX() + step * Math.sin(angleXOY),
                position.getY() + step * Math.cos(angleXOY), position.getZ());
        worldToViewCoordinatesConvertMatrix = null;
    }
    
    public void moveBack(double step) {
        position = new ThreeDoubleVector(position.getX() - step * Math.sin(angleXOY),
                position.getY() - step * Math.cos(angleXOY), position.getZ());
        worldToViewCoordinatesConvertMatrix = null;
    }

    public void zoom(double step) {
        position = new ThreeDoubleVector(
                position.getX() - normVector.getX() * step,
                position.getY() - normVector.getY() * step,
                position.getZ() - normVector.getZ() * step
        );
        worldToViewCoordinatesConvertMatrix = null;
    }

    private void updateNormVector() {
        normVector = new ThreeDoubleVector(Math.sin(angleXOY), Math.cos(angleXOY), Math.tan(angleZ)).normalized();
        worldToViewCoordinatesConvertMatrix = null;
    }
    
}