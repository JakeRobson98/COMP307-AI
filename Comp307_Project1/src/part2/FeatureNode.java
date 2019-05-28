package part2;

/**
 * Created by Jake on 10/04/18.
 */
public class FeatureNode implements Node{
    private Node right;
    private Node left;
    private String attName;
    FeatureNode(Node l, Node r, String attName){
        this.left = l;
        this.right = r;
        this.attName = attName;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public String getAttName() {
        return attName;
    }

    public void setAttName(String attName) {
        this.attName = attName;
    }

    public void report(String indent){
        System.out.format("%s%s = True:\n",
                indent, attName);
        left.report(indent+" ");
        System.out.format("%s%s = False:\n",
                indent, attName);
        right.report(indent+" ");
    }

    @Override
    public boolean isLeaf(){
        return false;
    }
}
