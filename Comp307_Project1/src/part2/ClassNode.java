package part2;

/**
 * Created by Jake on 9/04/18.
 */
public class ClassNode implements Node{


    private int ClassName;



    public ClassNode(int ClassName) {
        this.ClassName = ClassName;
    }

    public int getClassName() {
        return ClassName;
    }

    public String getClassString(){
        if(getClassName() == 0){
            return "live";
        }
        if(getClassName()== 1){
            return "die";
        }
        return "stay homerrrr";
    }

    public void setClassName(int className) {
        ClassName = className;
    }

    public void report(String indent){
        if (this == null)
            System.out.format("%sUnknown\n     ", indent);
        else
            System.out.format("%sClass %s\n",
                    indent, getClassString());
    }

    @Override
    public boolean isLeaf(){
        return true;
    }

    @Override
    public Node getLeft() {
        return null;
    }

    @Override
    public Node getRight() {
        return null;
    }
    @Override
    public String getAttName() {
        return getClassString();
    }
}
