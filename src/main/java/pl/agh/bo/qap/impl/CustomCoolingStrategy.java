package pl.agh.bo.qap.impl;

/**
 * @author Lukasz Raduj <raduj.lukasz@gmail.com>
 */
public class CustomCoolingStrategy extends DefaultCoolingStrategy implements ICoolingStrategy {
    private final double initialTemp;
    private final double coolingRate;
    private int nextSolutionAttempts;


    public CustomCoolingStrategy(double initialTemp, double coolingRate, int nextSolutionAttempts) {
        this.initialTemp = initialTemp;
        this.coolingRate = coolingRate;
        this.nextSolutionAttempts = nextSolutionAttempts;
    }

    @Override
    public double initialTemp() {
        return initialTemp;
    }

    @Override
    public double coolingRate() {
        return coolingRate;
    }

    public int nextSolutionAttempts() {
        return nextSolutionAttempts;
    }
}
