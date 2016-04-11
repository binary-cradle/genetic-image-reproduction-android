package com.binarycradle.gir.ga;


import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Vlad on 12-Dec-15.
 */
public class IndividualGenerator {

    public Individual generateRandomIndividual(GAParameters parameters) {
        List<Gene> dna = new ArrayList<Gene>();
        for (int i = 0; i < parameters.getDnaLength(); i++) {
            dna.add(generateRandomGene(parameters));
        }
        return new Individual(dna);
    }

    public Individual generateIndividualThroughCrossover(Match match, GAParameters parameters) {
        int firstDnaSize = match.getFirstIndividual().getDna().size();
        int secondDnaSize = match.getSecondIndividual().getDna().size();
        int maxDna = firstDnaSize;
        if (secondDnaSize > maxDna) {
            maxDna = secondDnaSize;
        }
        List<Gene> newDna = new ArrayList<Gene>();
        for (int i = 0; i < maxDna; i++) {
            if (Math.random() <= 0.5) {
                if (firstDnaSize > i) {
                    Gene gene = match.getFirstIndividual().getGene(i).clone();
                    gene.mutate(parameters);
                    newDna.add(gene);
                }
            } else {
                if (secondDnaSize > i) {
                    Gene gene = match.getSecondIndividual().getGene(i).clone();
                    gene.mutate(parameters);
                    newDna.add(gene);
                }
            }
        }
        return new Individual(newDna);
    }

    public Gene generateRandomGene(GAParameters parameters) {
        switch (parameters.getShapeType()) {
            case MIXED: {
                Random random = new Random();
                if (random.nextDouble() <= 0.5) {
                    return generateRandomLinearShape(parameters);
                } else {
                    return generateRandomRoundShape(parameters);
                }
            }
            case ROUND: {
                return generateRandomRoundShape(parameters);
            }
            case LINEAR:
            default: {
                return generateRandomLinearShape(parameters);
            }
        }
    }

    public Gene generateRandomLinearShape(GAParameters parameters) {
        Random random = new Random();
        int color = Color.argb(random.nextInt(256), random.nextInt(256), random.nextInt(256), random.nextInt(256));
        int geneSize = random.nextInt(parameters.getMaxGeneSize() / 4 - 2) + 2;
        List<Point> points = new ArrayList<Point>();
        for (int i = 0; i < geneSize; i++) {
            points.add(generateRandomPoint(parameters));
        }
        return new LinearShape(points, color);
    }

    public Gene generateRandomRoundShape(GAParameters parameters) {
        Random random = new Random();
        int color = Color.argb(random.nextInt(256), random.nextInt(256), random.nextInt(256), random.nextInt(256));
        int radiusLimit = (int) (parameters.getTargetImage().getWidth() * parameters.getMaxRoundShapeRatio());
        return new RoundShape(generateRandomPoint(parameters), random.nextInt(radiusLimit), color);
    }

    public Point generateRandomPoint(GAParameters parameters) {
        Random random = new Random();
        int x = random.nextInt(parameters.getTargetImage().getWidth() + 1);
        int y = random.nextInt(parameters.getTargetImage().getHeight() + 1);
        return new Point(x, y);
    }

}
