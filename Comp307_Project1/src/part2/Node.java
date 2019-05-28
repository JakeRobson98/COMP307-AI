package part2;

/**
 * Created by Jake on 10/04/18.
 */
public interface Node {
    public boolean isLeaf();

    public Node getLeft();

    public Node getRight();

    public String getAttName();

    public void report(String indent);
}
