package part3;

import java.util.Arrays;

/**
 * Created by Jake on 15/04/18.
 */
public class Feature {
    public int rows[];
    public int cols[];
    public int vals[];
    public Feature( int rows[], int cols[], int vals[]){
        this.rows = rows;
        this.cols = cols;
        this.vals = vals;
    }

    @Override
    public String toString() {
        return "Feature{" +
                "rows=" + Arrays.toString(rows) +
                ", cols=" + Arrays.toString(cols) +
                ", vals=" + Arrays.toString(vals) +
                '}';
    }

    public int[] getRows() {
        return rows;
    }

    public void setRows(int[] rows) {
        this.rows = rows;
    }

    public int[] getCols() {
        return cols;
    }

    public void setCols(int[] cols) {
        this.cols = cols;
    }

    public int[] getVals() {
        return vals;
    }

    public void setVals(int[] vals) {
        this.vals = vals;
    }
}
