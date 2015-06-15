package pl.agh.bo.qap.client;

import pl.agh.bo.qap.api.Distances;
import pl.agh.bo.qap.api.Facilities;
import pl.agh.bo.qap.api.Solution;
import pl.agh.bo.qap.impl.CustomCoolingStrategy;
import pl.agh.bo.qap.impl.ICoolingStrategy;
import pl.agh.bo.qap.impl.SimulatedAnnealingQapSolver;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

/**
 * @author Lukasz Raduj <raduj.lukasz@gmail.com>
 */
public class SimpleCmdClient {

    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("data/chr12a.dat"));
            String line;
            int size = Integer.parseInt(reader.readLine());
            int[] row = new int[size];
            int[][] rawDistances = new int[size][size];
            int[][] rawFacilities = new int[size][size];

            // read facilities
            for (int i = 0; i < size; i++) {
                line = reader.readLine();
                while (line.equals("")) {
                    line = reader.readLine();
                }

                String[] numbers = line.trim().split("\\s+");

                for (int j = 0; j < numbers.length; j++) {
                    row[j] = Integer.parseInt(numbers[j]);
                }

                rawFacilities[i] = Arrays.copyOf(row, size);
            }

            // read distances
            for (int i = 0; i < size; i++) {

                line = reader.readLine();
                while (line.equals("")) {
                    line = reader.readLine();
                }

                String[] numbers = line.trim().split("\\s+");

                for (int j = 0; j < numbers.length; j++) {
                    row[j] = Integer.parseInt(numbers[j]);
                }

                rawDistances[i] = Arrays.copyOf(row, size);
            }


            ICoolingStrategy[] strategies = new ICoolingStrategy[10];
            for (int i = 0; i < strategies.length; i++) {
                strategies[i] = new CustomCoolingStrategy(Double.MAX_VALUE, 0.1 + i * 0.01, 100 * (strategies.length - i));
            }

            Solution solution = SimulatedAnnealingQapSolver.main(
                    new Distances(rawDistances),
                    new Facilities(rawFacilities),
                    strategies);

            System.out.println(solution);

        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
    }
}
