package com.binarycradle.gir.ga;

import android.graphics.Bitmap;


/**
 * Created by Vlad on 12-Dec-15.
 */
public class GAParameters {

    private int dnaLength;
    private int maxGeneSize;
    private int populationSize;
    private boolean elitism;
    private int matchSampleSize;
    private Bitmap targetImage;
    private int[] targetPixels;
    private double mutationRatio;
    private double maxMutation;
    private long maxSumSquaredError;
    private double maxRoundShapeRatio;
    private ShapeType shapeType;

    public int getDnaLength() {
        return dnaLength;
    }

    public void setDnaLength(int dnaLength) {
        this.dnaLength = dnaLength;
    }

    public int getPopulationSize() {
        return populationSize;
    }

    public void setPopulationSize(int populationSize) {
        this.populationSize = populationSize;
    }

    public boolean isElitism() {
        return elitism;
    }

    public void setElitism(boolean elitism) {
        this.elitism = elitism;
    }

    public int getMatchSampleSize() {
        return matchSampleSize;
    }

    public void setMatchSampleSize(int matchSampleSize) {
        this.matchSampleSize = matchSampleSize;
    }

    public Bitmap getTargetImage() {
        return targetImage;
    }

    public void setTargetImage(Bitmap targetImage) {
        this.targetImage = targetImage;
        targetPixels = new int[targetImage.getWidth() * targetImage.getHeight()];
        targetImage.getPixels(targetPixels, 0, targetImage.getWidth(), 0, 0, targetImage.getWidth(), targetImage.getHeight());
    }

    public double getMutationRatio() {
        return mutationRatio;
    }

    public void setMutationRatio(double mutationRatio) {
        this.mutationRatio = mutationRatio;
    }

    public long getMaxSumSquaredError() {
        return maxSumSquaredError;
    }

    public void setMaxSumSquaredError(long maxSumSquaredError) {
        this.maxSumSquaredError = maxSumSquaredError;
    }

    public int getMaxMutation(int maxValue) {
        return (int) (maxValue * maxMutation);
    }

    public void setMaxMutation(double maxMutation) {
        this.maxMutation = maxMutation;
    }

    public int getMaxGeneSize() {
        return maxGeneSize;
    }

    public void setMaxGeneSize(int maxGeneSize) {
        this.maxGeneSize = maxGeneSize;
    }

    public double getMaxRoundShapeRatio() {
        return maxRoundShapeRatio;
    }

    public void setMaxRoundShapeRatio(double maxRoundShapeRatio) {
        this.maxRoundShapeRatio = maxRoundShapeRatio;
    }

    public ShapeType getShapeType() {
        return shapeType;
    }

    public void setShapeType(ShapeType shapeType) {
        this.shapeType = shapeType;
    }

    public int[] getTargetPixels() {
        return targetPixels;
    }
}
