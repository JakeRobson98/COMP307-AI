package part2; /**
 * Created by Jake on 9/04/18.
 */

import java.util.*;
import java.io.*;
import java.text.*;

public class DTmaker {
    int numCategories;
    int numAtts;
    int modeCat;
    Node root;
    List<String> categoryNames;
    List<String> attNames;
    final List <String> allAttributes = new ArrayList<String>();
    List<Instance> allInstances;
    List<Instance> expectedOutput;

    DTmaker(String fname, String fnameTest){
        readDataFile(fnameTest);
        //store expectedResults first
        expectedOutput = allInstances;
        //reset allInstances so that DT can be made from learning set
        readDataFile(fname);
        this.modeCat = getModeCat();
        for(String s: attNames){
            allAttributes.add(s);
        }
        this.root = BuildTree(this.allInstances, this.attNames);
        printTree(this.root);
        int wrongInstances = 0;
        for(Instance i : expectedOutput){

            if (!classification(i, root ).equals(getName(i.getCategory()))){
                wrongInstances++;
            }
            System.out.println("classified as: " + classification(i, root) + "|| expected class: " + getName(i.getCategory()));

        }
        System.out.println("number of wrong instances:" + wrongInstances);
        double correctInst = expectedOutput.size() - wrongInstances;
        double accuracy = correctInst / expectedOutput.size();
        DecimalFormat df = new DecimalFormat("####0.00");
        System.out.println("Accuracy = " +  df.format(accuracy* 100 ) + "%");
    }

    public String getName(int r){
        if(r== 0){
            return "live";
        }
        else{ return "die";}

    }


    public String classification(Instance I, Node n){
//        System.out.println("attssss::: " + allAttributes);
//        System.out.println(I);

        while(!n.isLeaf()){
            String att = n.getAttName();
            //System.out.println("att for the node : " + att);
            int i = getAttIndex(att);
            if(I.getAtt(i)){
//                System.out.println(I.getAtt(i));
//                System.out.println( "left =" + n.getLeft().getAttName());
                n = n.getLeft();
                return classification(I, n);
            }
            else{
//                System.out.println(I.getAtt(i));
//                System.out.println( "right =" + n.getRight().getAttName());
                n = n.getRight();

                return classification(I, n);
            }
        }
        //System.out.println(n.getAttName());
        return n.getAttName();
    }



    public void printTree(Node n){
        n.report(" ");
    }

    public Node BuildTree(List<Instance> instances, List<String> attributes){
        //If instances is empty return a class node with class of mode node across the whole set.
        if(instances.isEmpty()){
            return new ClassNode(this.modeCat);
        }
        //If Instances are pure return a leaf node with the class name of the instances
        if(isPure(instances)){
            return new ClassNode(instances.get(0).getCategory());
        }
        //If Attributes are empty return the majority class of instances at that node.
        if(attributes.isEmpty()){
            return new ClassNode(getMajorirtyclass(instances));
        }
        //Else, find the best attribute:
        else{
            Double bestImpurity = 100.0;
            String bestAttribute = null;
            List<Instance> bestTrueSet = null;
            List<Instance> bestFalseSet = null;
            // iterate through each attribute
            for (int i = 0; i < attributes.size(); i++){
//                System.out.println("iteration number: " + i);
//                System.out.println(attributes);
                //split into sets of true and false instances for tht attribute
                List<Instance> trueSet  = getTrueInstances(instances, getAttIndex(attributes.get(i)));
                List<Instance> falseSet = getFalseInstances(instances, getAttIndex(attributes.get(i)));
                //calculate the probabillity & Impurity of both sets
                double trueImpurity = calculateImpurity(trueSet);
                double falseImpurity = calculateImpurity(falseSet);
                double probTrue = getProbabalilty(trueSet, instances);
                double probFalse = getProbabalilty(falseSet, instances);
                //calculate average waited impurity of the Feature test.
                double averageWeightedImpurity = (trueImpurity * probTrue) + (falseImpurity*probFalse);
//                System.out.println( "t set size : " + trueSet.size() + attributes.get(i));
//                System.out.println("f set size : " + falseSet.size() + attributes.get(i));
//                System.out.println(" t impurity : " + trueImpurity + attributes.get(i));
//                System.out.println("f impuritr : " + falseImpurity + attributes.get(i));
//                System.out.println(" t prob : " + probTrue + attributes.get(i));
//                System.out.println("f prob : " + probFalse + attributes.get(i));
                //Replace the sets & attibutes based on the AW impurity
               // System.out.println(averageWeightedImpurity+  "  :" + attributes.get(i));
                if(averageWeightedImpurity < bestImpurity){
                    bestImpurity = averageWeightedImpurity;
                    bestTrueSet = trueSet;
                    bestFalseSet = falseSet;
                    bestAttribute = attributes.get(i);
                }
            }
            //remove the attribute from both sets

            Node left = BuildTree(bestTrueSet, getNewAttributes(attributes, bestAttribute));
            Node right = BuildTree(bestFalseSet, getNewAttributes(attributes, bestAttribute));

            return new FeatureNode(left,right, bestAttribute);
        }
    }
    public List<String> getNewAttributes(List<String> atts, String s){

        atts.remove(s);

        return atts;
    }
    public int getAttIndex(String s){
        for (int i = 0; i < allAttributes.size(); i++){
            if(s.equals(allAttributes.get(i))){
                return i;
            }
        }
        return 0;
    }

    public double getProbabalilty(List<Instance> subset, List<Instance> parentSet){
        double top = subset.size();
        double bottom = parentSet.size();
        return top/bottom;
    }
    public double calculateImpurity(List<Instance> l){
        if(l.size() < 2){
            return 0;
        }
        double purity;
        // 2mn/(m+n)^2
        int n = 0;
        int m = 0;
        for (Instance i: l){
            if(i.getCategory() == 0){
                n++;
            }
            else{
                m++;
            }
        }
        double top = (2*m*n);
        double bottom = (m+n)*(m+n);
        purity = top/ bottom;
        return purity;
    }

    public List<Instance> getTrueInstances(List<Instance> l, int attIndex){
        List<Instance> output = new ArrayList<Instance>();
        for(Instance i: l){
            if( i.getAtt(attIndex) == true){
                output.add(i);
            }
        }
        return output;
    }

    public List<Instance> getFalseInstances(List<Instance> l, int attIndex){
        List<Instance> output = new ArrayList<Instance>();
        for(Instance i: l){
            if( i.getAtt(attIndex) == false){
                output.add(i);
            }
        }
        return output;
    }


    public boolean isPure(List<Instance> l){
        int cat = l.get(0).getCategory();

        // a list of instances are pure, if they are all the same cat.
        for(Instance i : l){
            //System.out.println(i.getCategory() + "     " + cat);
            if(i.getCategory() != cat){
                return false;
            }
        }
        return true;
    }

    public int getModeCat(){
        return getMajorirtyclass(allInstances);
    }

    public int getMajorirtyclass(List<Instance> l){
        int cat1 = 0;
        int cat2 = 1;
        int count1, count2;
        count1 = count2 = 0;
        for(Instance i : l){
            if(i.getCategory() == 0){
                count1++;
            }
            else{
                count2++;
            }
        }
        if(count1 > count2){
            return 0;
        }
        else{
            return 1;
        }
    }


    public void readDataFile(String fname){
    /* format of names file:
     * names of categories, separated by spaces
     * names of attributes
     * category followed by true's and false's for each instance
     */
        System.out.println("Reading data from file "+fname);
        try {
            Scanner din = new Scanner(new File(fname));

            categoryNames = new ArrayList<String>();
            for (Scanner s = new Scanner(din.nextLine()); s.hasNext();) categoryNames.add(s.next());
            numCategories=categoryNames.size();
            System.out.println(numCategories +" categories");

            attNames = new ArrayList<String>();
            for (Scanner s = new Scanner(din.nextLine()); s.hasNext();) attNames.add(s.next());
            numAtts = attNames.size();
            System.out.println(numAtts +" attributes");

            allInstances = readInstances(din);
            din.close();
        }
        catch (IOException e) {
            throw new RuntimeException("Data File caused IO exception");
        }
    }

    public List<Instance> readInstances(Scanner din){
    /* instance = classname and space separated attribute values */
        List<Instance> instances = new ArrayList<Instance>();
        String ln;
        while (din.hasNext()){
            Scanner line = new Scanner(din.nextLine());
            instances.add(new Instance(categoryNames.indexOf(line.next()),line));
        }
        System.out.println("Read " + instances.size()+" instances");
        return instances;
    }

    public class Instance {

        private int category;
        private List<Boolean> vals;

        public Instance(int cat, Scanner s){
            category = cat;
            vals = new ArrayList<Boolean>();
            while (s.hasNextBoolean()) vals.add(s.nextBoolean());
        }

        public boolean getAtt(int index){
            return vals.get(index);
        }

        public int getCategory(){
            return category;
        }

        public String toString(){
            StringBuilder ans = new StringBuilder(categoryNames.get(category));
            ans.append(" ");
            for (Boolean val : vals)
                ans.append(val?"true  ":"false ");
            return ans.toString();
        }

    }

}
