package pl.agh.bo.qap.impl;

/**
 * @author Lukasz Raduj <raduj.lukasz@gmail.com>
 */
public class CustomCoolingStrategy extends DefaultCoolingStrategy implements ICoolingStrategy {
    private final double initialTemp;
    private final double coolingRate;


    public CustomCoolingStrategy(double initialTemp, double coolingRate) {
        this.initialTemp = initialTemp;
        this.coolingRate = coolingRate;
    }

    @Override
    public double initialTemp() {
        return initialTemp;
    }

    @Override
    public double coolingRate() {
        return coolingRate;
    }
}
