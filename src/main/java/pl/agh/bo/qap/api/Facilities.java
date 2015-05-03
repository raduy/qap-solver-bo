package pl.agh.bo.qap.api;

/**
 * @author Lukasz Raduj <raduj.lukasz@gmail.com>
 */
public class Facilities {

    private final int[][] facilities;

    public Facilities(int[][] facilities) {
        this.facilities = facilities;
    }

    public int[][] asRawArray() {
        return facilities;
    }
}
