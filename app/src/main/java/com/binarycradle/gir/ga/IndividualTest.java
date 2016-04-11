package com.binarycradle.gir.ga;

/**
 * Created by Vlad on 12-Dec-15.
 */
public class IndividualTest implements MutateableEntity{

    private byte[] dna;

    public IndividualTest(byte[] dna) {
        this.dna = dna;
    }

    @Override
    public void mutate(GAParameters parameters) {
        for (int i=0; i<dna.length;i++) {
            if (Math.random() <= parameters.getMutationRatio()) {
                if (dna[i] == 0) {
                    dna[i] = 1;
                } else {
                    dna[i] = 0;
                }
            }
        }
    }

    public byte getGene(int index) {
        return dna[index];
    }

    public void setGene(int index, byte gene) {
        dna[index] = gene;
    }

    public byte[] getDna() {
        return dna;
    }

    public String toString() {
        StringBuffer stringDna = new StringBuffer();
        for (int i=0; i<dna.length; i++) {
            stringDna.append(dna[i]);
        }
        return stringDna.toString();
    }
}
