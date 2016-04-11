package com.binarycradle.gir.ga;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vlad on 12-Dec-15.
 */
public class Matchmaker {

    public List<Match> getMatches(Generation generation, GAParameters parameters, int matchCount) {
        List<Individual> samplePopulation = getSamplePopulation(generation, parameters);
        List<Match> matches = new ArrayList<>();
        for (int i=0; i<matchCount; i++) {
            matches.add(getMatch(samplePopulation));
        }
        return matches;
    }

    private Match getMatch(List<Individual> samplePopulation) {
        int randomFirstIndex = (int) (Math.random() * samplePopulation.size());
        Individual firstIndividual = samplePopulation.get(randomFirstIndex);
        int randomSecondIndex = (int) (Math.random() * samplePopulation.size());
        while (randomSecondIndex == randomFirstIndex) {
            randomSecondIndex = (int) (Math.random() * samplePopulation.size());
        }
        Individual secondIndividual = samplePopulation.get(randomSecondIndex);
        return new Match(firstIndividual, secondIndividual);
    }

    private List<Individual> getSamplePopulation(Generation generation, GAParameters parameters) {
        List<Individual> sample = new ArrayList<Individual>();
        for (int i=0; i<parameters.getMatchSampleSize(); i++) {
            sample.add(generation.getPopulation().get(i));
        }
        return sample;
    }

}
