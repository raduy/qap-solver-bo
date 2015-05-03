package pl.agh.bo.qap.api;

import java.util.Arrays;

/**
 * @author Lukasz Raduj <raduj.lukasz@gmail.com>
 */
public class Solution {

    private final int[] orderedFacilities;
    private final double initialEnergy;
    private final double finalEnergy;

    public Solution(int[] orderedFacilities, double initialEnergy, double finalEnergy) {
        this.orderedFacilities = orderedFacilities;
        this.initialEnergy = initialEnergy;
        this.finalEnergy = finalEnergy;
    }

    public int[] getOrderedFacilities() {
        return orderedFacilities;
    }

    public double getInitialEnergy() {
        return initialEnergy;
    }

    public double getFinalEnergy() {
        return finalEnergy;
    }

    @Override
    public String toString() {
        return "Solution{" +
                "orderedFacilities=" + Arrays.toString(orderedFacilities) +
                ", initialEnergy=" + initialEnergy +
                ", finalEnergy=" + finalEnergy +
                '}';
    }
}
