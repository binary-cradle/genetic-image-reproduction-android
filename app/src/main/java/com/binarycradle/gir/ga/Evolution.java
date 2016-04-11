package com.binarycradle.gir.ga;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vlad on 12-Dec-15.
 */
public class Evolution {

    public List<Individual> generateNextGeneration(Generation previousGeneration, GAParameters parameters) {
        List<Individual> nextGeneration = new ArrayList<Individual>();
        performElitistStep(previousGeneration, nextGeneration, parameters);
        performCrossoverStep(previousGeneration, nextGeneration, parameters);
        performMutationStep(nextGeneration, parameters);
        return nextGeneration;
    }

    private void performElitistStep(Generation previousGeneration, List<Individual> nextGeneration, GAParameters parameters) {
        if (parameters.isElitism()) {
            Individual fittestIndividual = previousGeneration.getFittestIndividual(parameters);
            nextGeneration.add(fittestIndividual);
            if (parameters.getPopulationSize() == 2) {
                nextGeneration.add(fittestIndividual.clone());
            }
        }
    }

    private void performCrossoverStep(Generation previousGeneration, List<Individual> nextGeneration, GAParameters parameters) {
        if (parameters.getPopulationSize() > 2) {
            Matchmaker matchmaker = new Matchmaker();
            IndividualGenerator generator = new IndividualGenerator();
            int matchCount = parameters.getPopulationSize();
            if (parameters.isElitism()) {
                matchCount--;
            }
            List<Match> matches = matchmaker.getMatches(previousGeneration, parameters, matchCount);
            for (Match match : matches) {
                nextGeneration.add(generator.generateIndividualThroughCrossover(match, parameters));
            }
        }
    }

    private void performMutationStep(List<Individual> nextGeneration, GAParameters parameters) {
        int offset = 0;
        if (parameters.isElitism()) {
            offset = 1;
        }
        for (int i=offset; i<parameters.getPopulationSize(); i++) {
            nextGeneration.get(i).mutate(parameters);
        }
    }

}
