package part3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.List;

/**
 * Created by Jake on 15/04/18.
 */
public class Image {

    public int pixels[][];
    public Character type;
    public int width;
    public int height;
    public List<Integer> featureValues;

    @Override
    public String toString() {
        return "Image{" +
                "pixels=" + Arrays.toString(pixels) +
                ", type=" + type +
                ", width=" + width +
                ", height=" + height +
                //", featurevalues=" + featureValues.toString() +
                '}';
    }

    public Image(int w, int h, int pixels[][] , char type){
        this.width = w;
        this.height = h;
        this.pixels = pixels;
        this.type = type;

    }

    public void setFeatureValues(ArrayList<Integer> featureValues){
        this.featureValues = featureValues;
    }
}
