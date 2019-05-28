package part1;

/**
 * Created by Jake on 12/04/18.
 */
public class InstanceCount {
    private Instance.classType type;
    private double distanceToUnseen;
    public InstanceCount(Instance.classType t, double d){
        this.type = t;
        this.distanceToUnseen = d;
    }

    public Instance.classType getType() {
        return type;
    }

    public void setType(Instance.classType type) {
        this.type = type;
    }

    public double getDistanceToUnseen() {
        return distanceToUnseen;
    }

    public void setDistanceToUnseen(double distanceToUnseen) {
        this.distanceToUnseen = distanceToUnseen;
    }
}
