package pl.agh.bo.qap.impl;

import pl.agh.bo.qap.api.Distances;
import pl.agh.bo.qap.api.Facilities;
import pl.agh.bo.qap.api.IQapSolver;
import pl.agh.bo.qap.api.Solution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static java.lang.System.*;

/**
 * @author Lukasz Raduj <raduj.lukasz@gmail.com>
 */
public class SimulatedAnnealingQapSolver extends Thread implements IQapSolver {

    private int[][] distances;
    private int[][] facilities;
    private ICoolingStrategy coolingStrategy;
    private final Random random = new Random(currentTimeMillis());

    private static final Object OBJ_LOCK = new Object();
    private static int threads;
    private static volatile int[] bestSolution;
    private static volatile double bestSolutionEnergy;
    private static volatile String bestSolutionId;
    private int nextSolutionAttempts;

    public SimulatedAnnealingQapSolver(Distances distances, Facilities facilities, ICoolingStrategy coolingStrategy, int nextSolutionAttempts) {
        this.distances = distances.asRawArray();
        this.facilities = facilities.asRawArray();
        this.coolingStrategy = coolingStrategy;
        this.nextSolutionAttempts = nextSolutionAttempts;
    }

    @Override
    public void run() {
        this.solve();
    }

    public static Solution main(Distances distances, Facilities facilities, ICoolingStrategy[] strategies) {
        threads = strategies.length;
        long startTime = System.currentTimeMillis();
        List<Thread> threadList = new ArrayList<Thread>();

        for(int i = 0; i < threads; i++) {
            SimulatedAnnealingQapSolver solver = new SimulatedAnnealingQapSolver(distances, facilities, strategies[i], strategies[i].nextSolutionAttempts());
            threadList.add(solver);

            solver.setName("Thread #" + i);
            solver.start();
        }

        for(Thread t : threadList) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        long estimatedTime = System.currentTimeMillis() - startTime;
        Solution result = new Solution(bestSolution, 0, bestSolutionEnergy);

        System.out.println("");
        System.out.println(estimatedTime + " ms");
        return result;
    }

    private void solve() {
        double temperature = coolingStrategy.initialTemp();
        double coolingRate = coolingStrategy.coolingRate();

        int[] solution = drawInitialSolution(distances.length);
        double initialEnergy = computeEnergy(solution);

        synchronized (OBJ_LOCK) {
            if(bestSolution == null) {
                bestSolution = solution;
                bestSolutionId = this.getName();
                bestSolutionEnergy = initialEnergy;
            }
        }

        while (!coolingStrategy.shouldStop(solution, temperature)) {
            for (int i = 0; i < nextSolutionAttempts; ++i) {
                int[] nextSolution = findNextSolution(solution);

                updateBestSolutionIfNecessary(nextSolution);

                if (shouldAcceptSolution(nextSolution, solution, temperature)) {
                    solution = nextSolution;
                }
            }

            temperature = temperature * (1 - coolingRate);
        }

        System.out.println(this.getName() + ": " + new Solution(bestSolution, initialEnergy, bestSolutionEnergy));
    }

    private void updateBestSolutionIfNecessary(int[] solution) {
        synchronized (OBJ_LOCK) {
            double energy = computeEnergy(solution);
            if (energy < bestSolutionEnergy) {
                bestSolution = solution;
                bestSolutionId = this.getName();
                bestSolutionEnergy = energy;
            }
        }
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

        int energy = 0;
        for (int i = 0; i < facilitiesLength; i++) {
            for (int j = 0; j < facilitiesLength; j++) {
                energy += facilities[i][j] * distances[solution[i]][solution[j]];
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

    public Solution solve(Distances distances, Facilities facilities, ICoolingStrategy coolingStrategy) {
        return null;
    }
}
