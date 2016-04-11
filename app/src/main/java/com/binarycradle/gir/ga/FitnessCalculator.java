package com.binarycradle.gir.ga;

import android.graphics.Bitmap;
import android.graphics.Color;


/**
 * Created by Vlad on 12-Dec-15.
 */
public class FitnessCalculator {

//    private static byte[] targetDna;
//
//    public void setTargetDna(byte[] targetDna) {
//        this.targetDna = targetDna;
//    }

//    public void setTargetDna(String targetDna) {
//        this.targetDna = new byte[targetDna.length()];
//        for (int i=0; i<targetDna.length(); i++) {
//            if (targetDna.charAt(i) == '1') {
//                this.targetDna[i] = 1;
//            } else {
//                this.targetDna[i] = 0;
//            }
//        }
//    }

//    public double getFitness(Individual individual) {
//        int accurateGeneCount = 0;
//        for (int i=0; i<targetDna.length; i++) {
//            if (individual.getGene(i) == targetDna[i]) {
//                accurateGeneCount++;
//            }
//        }
//        return (double)accurateGeneCount / targetDna.length;
//    }

    public long getFitness(Individual individual, GAParameters parameters) {
        long timestamp = System.currentTimeMillis();
        long sumSquaredError = 0;
        Bitmap targetImage = parameters.getTargetImage();
        Bitmap individualImage = individual.getImage(parameters);

        int[] targetPixels = parameters.getTargetPixels();

        int[] individualPixels = new int[individualImage.getWidth() * individualImage.getHeight()];
        individualImage.getPixels(individualPixels, 0, individualImage.getWidth(), 0, 0, individualImage.getWidth(), individualImage.getHeight());

        for (int i=0; i< individualPixels.length; i++) {
                int targetColor = targetPixels[i];
                int individualColor = individualPixels[i];
                int redDifference = Color.red(targetColor) - Color.red(individualColor);
                int greenDifference = Color.green(targetColor) - Color.green(individualColor);
                int blueDifference = Color.blue(targetColor) - Color.blue(individualColor);
                sumSquaredError += (redDifference * redDifference) + (greenDifference * greenDifference) + (blueDifference * blueDifference);
        }
//        return 1 - ((double)sumSquaredError / parameters.getMaxSumSquaredError());
        System.out.println("fitness took : " + (System.currentTimeMillis() - timestamp));
        return sumSquaredError;
    }

    public long getMaxSumSquaredError(Bitmap image) {
        long maxDifferencePerPixel = 255 * 255 * 3;
        return maxDifferencePerPixel * image.getHeight() * image.getWidth();
    }

}
