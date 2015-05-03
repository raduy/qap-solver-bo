package pl.agh.bo.qap.api;

import pl.agh.bo.qap.impl.ICoolingStrategy;

/**
 * @author Lukasz Raduj <raduj.lukasz@gmail.com>
 */
public interface IQapSolver {

    Solution solve(Distances distances, Facilities facilities, ICoolingStrategy coolingStrategy);
}
