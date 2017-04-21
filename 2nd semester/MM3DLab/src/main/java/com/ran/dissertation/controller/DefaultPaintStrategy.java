package com.ran.dissertation.controller;

import com.ran.dissertation.algebraic.common.ArithmeticOperations;
import com.ran.dissertation.algebraic.common.Pair;
import com.ran.dissertation.algebraic.matrix.DoubleMatrix;
import com.ran.dissertation.algebraic.vector.ThreeDoubleVector;
import com.ran.dissertation.algebraic.vector.TwoDoubleVector;
import com.ran.dissertation.algebraic.vector.TwoIntVector;
import com.ran.dissertation.ui.ImagePanelPaintStrategy;
import com.ran.dissertation.ui.PaintDelegate;
import com.ran.dissertation.world.Camera;
import com.ran.dissertation.world.DisplayableObject;
import com.ran.dissertation.world.World;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DefaultPaintStrategy implements ImagePanelPaintStrategy {

    private World world;

    public DefaultPaintStrategy(World world) {
        this.world = world;
    }
    
    @Override
    public void paint(PaintDelegate paintDelegate, int width, int height) {
        for (DisplayableObject displayableObject: world.getDisplayableObjects()) {
            paintDisplayableObject(displayableObject, world.getCamera(), paintDelegate, width, height);
        }
    }
    
    private void paintDisplayableObject(DisplayableObject displayableObject, Camera camera,
            PaintDelegate paintDelegate, int width, int height) {
        if (!displayableObject.isVisible()) {
            return;
        }
        List<TwoIntVector> displayCoordinates = convertWorldCoordinatesToDiplayCoordinates(
                displayableObject.getCurrentFigureVertices(), camera, width, height);
        for (Pair<Integer, Integer> figureEdge: displayableObject.getFigure().getFigureEdges()) {
            TwoIntVector firstPoint = displayCoordinates.get(figureEdge.getLeft());
            TwoIntVector secondPoint = displayCoordinates.get(figureEdge.getRight());
            if (firstPoint == null || secondPoint == null) {
                continue;
            }
            paintDelegate.putLine(firstPoint, secondPoint, displayableObject.getColor(),
                    displayableObject.getEdgePaintWidth());
        }
        for (TwoIntVector point: displayCoordinates) {
            if (point != null) {
                paintDelegate.putCircle(point, displayableObject.getVerticePaintRadius(),
                        displayableObject.getColor());
            }
        }
    }
    
    private List<TwoIntVector> convertWorldCoordinatesToDiplayCoordinates(List<ThreeDoubleVector> worldCoordinates,
            Camera camera, int width, int height) {
        DoubleMatrix worldCoordinatesMatrix = convertVectorsToMatrixWithHomogeneousCoordinates(worldCoordinates);
        DoubleMatrix viewCoordinatesMatrix = convertWorldCoordinatesToViewCoorninates(worldCoordinatesMatrix, camera);
        List<TwoDoubleVector> projectionCoordinates = convertViewCoordinatesToProjectionCoordinates(viewCoordinatesMatrix, camera);
        List<TwoIntVector> displayCoordinates = convertProjectionCoordinatesToDisplayCoordinates(projectionCoordinates, camera, width, height);
        return displayCoordinates;
    }
    
    private DoubleMatrix convertVectorsToMatrixWithHomogeneousCoordinates(List<ThreeDoubleVector> vectors) {
        double[][] matrix = new double[4][vectors.size()];
        for (int i = 0; i < vectors.size(); i++) {
            ThreeDoubleVector vector = vectors.get(i);
            matrix[0][i] = vector.getX();
            matrix[1][i] = vector.getY();
            matrix[2][i] = vector.getZ();
            matrix[3][i] = 1.0;
        }
        return new DoubleMatrix(matrix);
    }
    
    private DoubleMatrix convertWorldCoordinatesToViewCoorninates(DoubleMatrix worldCoorninatesMatrix, Camera camera) {
        DoubleMatrix convertMatrix = getCameraWorldToViewCoordinatesConvertMatrix(camera);
        return convertMatrix.multiply(worldCoorninatesMatrix);
    }
    
    private List<TwoDoubleVector> convertViewCoordinatesToProjectionCoordinates(DoubleMatrix viewCoordinatesMatrix, Camera camera) {
        DoubleMatrix convertMatrix = getCameraViewToProjectionCoordinatesConvertMatrix(camera);
        DoubleMatrix projectionCoordinatesMatrix = convertMatrix.multiply(viewCoordinatesMatrix);
        List<TwoDoubleVector> projectionCoordinates = new ArrayList<>(projectionCoordinatesMatrix.getColumns());
        for (int i = 0; i < projectionCoordinatesMatrix.getColumns(); i++) {
            if (ArithmeticOperations.doubleGreaterOrEquals(viewCoordinatesMatrix.get(2, i) / viewCoordinatesMatrix.get(3, i), 0.0)) {
                projectionCoordinates.add(null);
            } else {
                double homogeneousCoordinate = projectionCoordinatesMatrix.get(2, i);
                double x = projectionCoordinatesMatrix.get(0, i) / homogeneousCoordinate;
                double y = projectionCoordinatesMatrix.get(1, i) / homogeneousCoordinate;
                projectionCoordinates.add(new TwoDoubleVector(x, y));
            }
        }
        return projectionCoordinates;
    }
    
    private List<TwoIntVector> convertProjectionCoordinatesToDisplayCoordinates(List<TwoDoubleVector> projectionCoordinates,
            Camera camera, int displayWidth, int displayHeight) {
        double lensWidth = camera.getLensWidth();
        double lensHeight = lensWidth * ((double)displayHeight / (double)displayWidth);
        double lensLeft = -lensWidth / 2.0;
        double lensTop = lensHeight / 2.0;
        return projectionCoordinates.stream().sequential()
                .map(point -> {
                    if (point == null) {
                        return null;
                    }
                    int x = (int) Math.round((point.getX() - lensLeft) / lensWidth * displayWidth);
                    int y = (int) Math.round((lensTop - point.getY()) / lensHeight * displayHeight);
                    return new TwoIntVector(x, y);
                }).collect(Collectors.toList());
    }
    
    private DoubleMatrix getCameraWorldToViewCoordinatesConvertMatrix(Camera camera) {
        DoubleMatrix convertMatrix = camera.getWorldToViewCoordinatesConvertMatrix();
        if (convertMatrix == null) {
            ThreeDoubleVector normVector = camera.getNormVector();
            ThreeDoubleVector verticalVector = camera.getVerticalVector();

            ThreeDoubleVector baseK = normVector;
            ThreeDoubleVector baseI = verticalVector.multiply(normVector).normalized();
            ThreeDoubleVector baseJ = normVector.multiply(baseI).normalized();
            List<ThreeDoubleVector> basis = Arrays.asList(baseI, baseJ, baseK);

            double[][] coordinatesChangingAffineMatrix = new double[4][4];
            for (int i = 0; i < 3; i++) {
                coordinatesChangingAffineMatrix[i][0] = basis.get(i).getX();
                coordinatesChangingAffineMatrix[i][1] = basis.get(i).getY();
                coordinatesChangingAffineMatrix[i][2] = basis.get(i).getZ();
                coordinatesChangingAffineMatrix[i][3] = -basis.get(i).scalarMultiply(camera.getPosition());
            }
            coordinatesChangingAffineMatrix[3][3] = 1.0;
            convertMatrix = new DoubleMatrix(coordinatesChangingAffineMatrix);
            camera.setWorldToViewCoordinatesConvertMatrix(convertMatrix);
        }
        return convertMatrix;
    }
    
    private DoubleMatrix getCameraViewToProjectionCoordinatesConvertMatrix(Camera camera) {
        DoubleMatrix convertMatrix = camera.getViewToProjectionCoordinatesConvertMatrix();
        if (convertMatrix == null) {
            double d = camera.getReversedDistanceBehind();
            convertMatrix = new DoubleMatrix(new double[][] {
                {1.0, 0.0, 0.0, 0.0},
                {0.0, 1.0, 0.0, 0.0},
                {0.0, 0.0, -d, 1.0}
            });
            camera.setViewToProjectionCoordinatesConvertMatrix(convertMatrix);
        }
        return convertMatrix;
    }

}