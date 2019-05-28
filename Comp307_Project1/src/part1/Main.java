package part1;
import java.io.File;

/**
 * Created by Jake on 12/04/18.
 */
public class Main {
    public static void main(String[] args) {
        Classifier c = new Classifier(new File(args[0]),new File(args[1]));
    }
}
