package com.binarycradle.gir.ga;

/**
 * Created by Vlad on 12-Dec-15.
 */
public class Match {

    private Individual firstIndividual;
    private Individual secondIndividual;

    public Match(Individual firstIndividual, Individual secondIndividual) {
        this.firstIndividual = firstIndividual;
        this.secondIndividual = secondIndividual;
    }

    public Individual getFirstIndividual() {
        return firstIndividual;
    }

    public Individual getSecondIndividual() {
        return secondIndividual;
    }

}
