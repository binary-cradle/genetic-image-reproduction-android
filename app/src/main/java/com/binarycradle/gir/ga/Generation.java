package com.binarycradle.gir.ga;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Vlad on 12-Dec-15.
 */
public class Generation {

    private List<Individual> population;

    public Generation(GAParameters parameters) {
        IndividualGenerator generator = new IndividualGenerator();
        population = new ArrayList<Individual>();
        for (int i=0; i<parameters.getPopulationSize(); i++) {
            population.add(generator.generateRandomIndividual(parameters));
        }
        sortPopulation(parameters);
    }

    public Generation(List<Individual> population) {
        this.population = population;
    }

    public Generation(Generation previousGeneration, GAParameters parameters) {
        Evolution evolution = new Evolution();
        this.population = evolution.generateNextGeneration(previousGeneration, parameters);
        sortPopulation(parameters);
    }

    public Individual getFittestIndividual(GAParameters parameters) {
        return population.get(0);
//        long minError = parameters.getMaxSumSquaredError();
//        Individual fittestIndividual = null;
//        for (Individual individual : population) {
//            long error = individual.getFitness(parameters);
//            if (error < minError) {
//                minError = error;
//                fittestIndividual = individual;
//            }
//        }
//        return fittestIndividual;
    }

    public List<Individual> getPopulation() {
        return population;
    }

    private void sortPopulation(final GAParameters parameters) {
        Comparator<Individual> individualComparator = new Comparator<Individual>() {
            @Override
            public int compare(Individual o1, Individual o2) {
                if (o1.getFitness(parameters) < o2.getFitness(parameters)) {
                    return -1;
                } else if (o1.getFitness(parameters) > o2.getFitness(parameters)) {
                    return 1;
                } else {
                    return 0;
                }
            }
        };
        Collections.sort(population, individualComparator);
    }

}