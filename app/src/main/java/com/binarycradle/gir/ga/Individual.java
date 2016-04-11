package com.binarycradle.gir.ga;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Vlad on 12-Dec-15.
 */
public class Individual implements MutateableEntity {

    private List<Gene> dna;
    private Bitmap image;
    private boolean changed;
    private long fitness;

    public Individual(List<Gene> dna) {
        this.dna = dna;
        fitness = -1;
    }

    @Override
    public void mutate(GAParameters parameters) {
        performGeneMutation(parameters);
        performGeneCountMutation(parameters);
    }

    private void performGeneMutation(GAParameters parameters) {
        Random random = new Random();
//        if (random.nextDouble() <= parameters.getMutationRatio()) {
//            dna.get(random.nextInt(dna.size())).mutate(parameters);
//        }
        for (Gene gene : dna) {
            gene.mutate(parameters);
        }
        changed = true;
    }

    private void performGeneCountMutation(GAParameters parameters) {
        Random random = new Random();
        IndividualGenerator generator = new IndividualGenerator();
        if (random.nextDouble() <= parameters.getMutationRatio()) {
            dna.add(generator.generateRandomGene(parameters));
        }
        if (random.nextDouble() <= parameters.getMutationRatio()) {
            dna.add(dna.get(random.nextInt(dna.size())).clone());
        }
        if (dna.size() > 1 && random.nextDouble() <= parameters.getMutationRatio()) {
            dna.remove(random.nextInt(dna.size()));
        }
    }

    public Bitmap getImage(GAParameters parameters) {
        if (image == null || changed) {
            Renderer renderer = new Renderer();
            image = renderer.generateImageFromDna(dna, parameters);
        }
        return image;
    }

    public Individual clone() {
        List<Gene> cloneDna = new ArrayList<Gene>();
        for (Gene gene : dna) {
            cloneDna.add(gene.clone());
        }
        return new Individual(cloneDna);
    }

    public Gene getGene(int index) {
        return dna.get(index);
    }

    public void setGene(int index, Gene gene) {
        dna.set(index, gene);
        changed = true;
    }

    public long getFitness(GAParameters parameters) {
        if (fitness == -1 || changed) {
            FitnessCalculator fitnessCalculator = new FitnessCalculator();
            fitness = fitnessCalculator.getFitness(this, parameters);
            changed = false;
        }
        return fitness;
    }

    public List<Gene> getDna() {
        return dna;
    }

    public String toString() {
        return "Number of genes: " + dna.size();
    }
}
