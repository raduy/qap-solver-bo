package pl.agh.bo.qap.api;

/**
 * @author Lukasz Raduj <raduj.lukasz@gmail.com>
 */
public class Distances {
    private final int[][] distances;

    public Distances(int[][] distances) {
        this.distances = distances;
    }

    public int[][] asRawArray() {
        return distances;
    }
}
