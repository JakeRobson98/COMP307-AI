package part1;

/**
 * Created by Jake on 12/04/18.
 */
public class Instance {
    public enum classType{
        setosa, virginica, veriscolor, unclassified
    }

    private classType type;
    private double sepalLength;
    private double sepalWidth;
    private double petalLength;
    private double petalWidth;

    public Instance(double sl, double sw, double pl, double pw, String type){
        this.sepalLength = sl;
        this.sepalWidth = sw;
        this.petalLength = pl;
        this.petalWidth = pw;
        if(type != null){
            this.type = getTypefromString(type);
        }
    }
    public Instance.classType getTypefromString(String s){
        if(s.contains("Iris-versicolor")){
            return classType.veriscolor;
        }
        if (s.contains("Iris-virginica")){
            return classType.virginica;
        }
        if (s.contains("Iris-setosa")){
            return classType.setosa;
        }
        return classType.unclassified;

    }

    @Override
    public String toString() {
        return "Instance{" +
                "type=" + type +
                ", sepalLength=" + sepalLength +
                ", sepalWidth=" + sepalWidth +
                ", petalLength=" + petalLength +
                ", petalWidth=" + petalWidth +
                '}';
    }

    public classType getType() {
        return type;
    }

    public void setType(classType type) {
        this.type = type;
    }

    public double getSepalLength() {
        return sepalLength;
    }

    public void setSepalLength(double sepalLength) {
        this.sepalLength = sepalLength;
    }

    public double getSepalWidth() {
        return sepalWidth;
    }

    public void setSepalWidth(double sepalWidth) {
        this.sepalWidth = sepalWidth;
    }

    public double getPetalLength() {
        return petalLength;
    }

    public void setPetalLength(double petalLength) {
        this.petalLength = petalLength;
    }

    public double getPetalWidth() {
        return petalWidth;
    }

    public void setPetalWidth(double petalWidth) {
        this.petalWidth = petalWidth;
    }
}
