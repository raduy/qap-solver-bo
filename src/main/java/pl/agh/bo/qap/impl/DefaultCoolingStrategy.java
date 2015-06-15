package pl.agh.bo.qap.impl;

/**
 * @author Lukasz Raduj <raduj.lukasz@gmail.com>
 */
public class DefaultCoolingStrategy implements ICoolingStrategy {
    public double initialTemp() {
        return 100000.0;
    }

    public double coolingRate() {
        return 0.01;
    }

    public boolean shouldStop(int[] solution, double temperature) {
        return temperature - 1.0 < 0;
    }

    public int nextSolutionAttempts() {
        return 100;
    }
}
