package pl.agh.bo.qap.impl;

import pl.agh.bo.qap.api.Distances;
import pl.agh.bo.qap.api.Facilities;
import pl.agh.bo.qap.api.IQapSolver;
import pl.agh.bo.qap.api.Solution;

import java.util.Arrays;
import java.util.Random;

import static com.google.common.base.Preconditions.checkArgument;
import static java.lang.System.*;

/**
 * @author Lukasz Raduj <raduj.lukasz@gmail.com>
 */
public class SimulatedAnnealingQapSolver implements IQapSolver {

    private int[][] distances;
    private int[][] facilities;
    private final Random random = new Random(currentTimeMillis());

    @Override
    public Solution solve(Distances distances, Facilities facilities, ICoolingStrategy coolingStrategy) {
        return solve(distances.asRawArray(), facilities.asRawArray(), coolingStrategy);
    }

    private Solution solve(int[][] distances, int[][] facilities, ICoolingStrategy coolingStrategy) {
        String errorMessage = "Distances must have equal size to facilities";
        checkArgument(distances.length == facilities.length, errorMessage);

        this.distances = distances;
        this.facilities = facilities;
        double temperature = coolingStrategy.initialTemp();
        double coolingRate = coolingStrategy.coolingRate();

        int[] solution = drawInitialSolution(distances.length);
        double initialEnergy = computeEnergy(solution);

        int[] bestFound = solution;
        while (!coolingStrategy.shouldStop(solution, temperature)) {
            int[] nextSolution = findNextSolution(solution);

            if (computeEnergy(solution) < computeEnergy(bestFound)) {
                bestFound = solution;
            }
            if (shouldAcceptSolution(nextSolution, solution, temperature)) {
                solution = nextSolution;
            }
            temperature = temperature * (1 - coolingRate);
        }

        double finalEnergy = computeEnergy(solution);

        return new Solution(solution, initialEnergy, finalEnergy);
    }

    private boolean shouldAcceptSolution(int[] nextSolution, int[] solution, double temperature) {
        int newEnergy = computeEnergy(nextSolution);
        int oldEnergy = computeEnergy(solution);

        if (newEnergy < oldEnergy) {
            return true;
        }

        double acceptanceProbability = Math.exp(-(newEnergy - oldEnergy) / temperature);
        double randomDouble = random.nextDouble();

        return acceptanceProbability > randomDouble;
    }

    private int[] findNextSolution(int[] solution) {
        int length = solution.length;
        int[] nextSolution = Arrays.copyOf(solution, length);

        int facilityOne = random.nextInt(length);
        int facilityTwo = random.nextInt(length);

        while (facilityOne == facilityTwo) {
            facilityTwo = random.nextInt(length);
        }

        return swap(nextSolution, facilityOne, facilityTwo);
    }

    private int[] drawInitialSolution(int length) {
        int[] initSolution = new int[length];
        for (int i = 0; i < initSolution.length; i++) {
            initSolution[i] = i;
        }

        for (int i = 0; i < initSolution.length; i++) {
            int toSwap = random.nextInt(initSolution.length - i) + i;
            swap(initSolution, i, toSwap);
        }
        return initSolution;
    }

    private int computeEnergy(int[] solution) {
        int facilitiesLength = facilities.length;

        int[] invertedSolution = invertSolution(solution);

        int energy = 0;
        for (int i = 0; i < facilitiesLength; i++) {
            for (int j = i; j < facilitiesLength; j++) {
                int locationOne = invertedSolution[i];
                int locationTwo = invertedSolution[j];

                energy += distances[locationOne][locationTwo] * facilities[i][j];
            }
        }

        return energy;
    }

    private static int[] swap(int[] array, int i, int j) {
        int tmp = array[i];
        array[i] = array[j];
        array[j] = tmp;

        return array;
    }

    private static int[] invertSolution(int[] solution) {
        int length = solution.length;
        int[] invertedSolution = new int[length];

        for (int i = 0; i < length; i++) {
            invertedSolution[solution[i]] = i;
        }

        return invertedSolution;
    }
}
