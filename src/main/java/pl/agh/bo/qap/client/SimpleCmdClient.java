package pl.agh.bo.qap.client;

import pl.agh.bo.qap.api.Distances;
import pl.agh.bo.qap.api.Facilities;
import pl.agh.bo.qap.api.IQapSolver;
import pl.agh.bo.qap.api.Solution;
import pl.agh.bo.qap.impl.CustomCoolingStrategy;
import pl.agh.bo.qap.impl.DefaultCoolingStrategy;
import pl.agh.bo.qap.impl.SimulatedAnnealingQapSolver;

/**
 * @author Lukasz Raduj <raduj.lukasz@gmail.com>
 */
public class SimpleCmdClient {

    public static void main(String[] args) {
//        playWithSmallSizeProblem();
        playWithBiggerSizeProblem();
    }

    private static void playWithSmallSizeProblem() {

        Distances distances = new Distances(new int[][]{
                {0, 22, 53, 53},
                {22, 0, 40, 62},
                {53, 40, 0, 55},
                {53, 62, 55, 0}});

        Facilities facilities = new Facilities(new int[][]{
                {0, 3, 0, 2},
                {3, 0, 0, 1},
                {0, 0, 0, 4},
                {2, 1, 4, 0}});


        CustomCoolingStrategy coolingStrategy = new CustomCoolingStrategy(Double.MAX_VALUE, 0.001);

        Solution solution = SimulatedAnnealingQapSolver.main(4, distances, facilities, coolingStrategy);

        //Optimum here is 395
        System.out.println(solution);
    }

    private static void playWithBiggerSizeProblem() {

        Distances distances = new Distances(new int[][]{
                {0, 1, 2, 3, 1, 2, 3, 4},
                {1, 0, 1, 2, 2, 1, 2, 3},
                {2, 1, 0, 1, 3, 2, 1, 2},
                {3, 2, 1, 0, 4, 3, 2, 1},
                {1, 2, 3, 4, 0, 1, 2, 3},
                {2, 1, 2, 3, 1, 0, 1, 2},
                {3, 2, 1, 2, 2, 1, 0, 1},
                {4, 3, 2, 1, 3, 2, 1, 0}
        });

        Facilities facilities = new Facilities(new int[][]{
                {0, 5, 2, 4, 1, 0, 0, 6},
                {5, 0, 3, 0, 2, 2, 2, 0},
                {2, 3, 0, 0, 0, 0, 0, 5},
                {4, 0, 0, 0, 5, 1, 1, 10},
                {1, 2, 0, 5, 0, 10, 10, 0},
                {0, 2, 0, 1, 10, 0, 5, 10},
                {0, 2, 0, 1, 10, 5, 0, 1},
                {6, 0, 5, 10, 0, 10, 1, 0}
        });

        CustomCoolingStrategy coolingStrategy = new CustomCoolingStrategy(Double.MAX_VALUE, 0.001);

        Solution solution = SimulatedAnnealingQapSolver.main(4, distances, facilities, coolingStrategy);

        //Optimum here is 107
        System.out.println(solution);
    }
}
