package pl.agh.bo.qap.impl;

/**
 * @author Lukasz Raduj <raduj.lukasz@gmail.com>
 */
public interface ICoolingStrategy {

    double initialTemp();

    double coolingRate();

    boolean shouldStop(int[] solution, double temperature);

    int nextSolutionAttempts();
}
