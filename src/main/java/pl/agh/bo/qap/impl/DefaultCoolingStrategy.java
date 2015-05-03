package pl.agh.bo.qap.impl;

/**
 * @author Lukasz Raduj <raduj.lukasz@gmail.com>
 */
public class DefaultCoolingStrategy implements ICoolingStrategy {
    @Override
    public double initialTemp() {
        return 100000.0;
    }

    @Override
    public double coolingRate() {
        return 0.01;
    }

    @Override
    public boolean shouldStop(int[] solution, double temperature) {
        return temperature - 1.0 < 0;
    }
}
