package part1;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.io.File;
import java.util.PriorityQueue;
import java.util.Scanner;
/**
 * Created by Jake on 3/04/18.
 */
public class Classifier {
    private ArrayList<Instance> training;
    private ArrayList<Instance> testSet;
    private ArrayList<Instance> expectedOuput;

    private double pWrange;
    private double pLrange;
    private double sWrange;
    private double sLrange;

    public Classifier(File training, File test) {
        this.training = makeInstances(training, true);
        this.expectedOuput = makeInstances(training, true);
        this.testSet = makeInstances(test,false);

        getRange();
        compareInstances();
        //System.out.println(testSet);
        double count = 0;
        for(int i = 0; i < testSet.size(); i++){
            if (testSet.get(i).getType() != this.expectedOuput.get(i).getType()){
                count++;
                System.out.println("Classified as: " +testSet.get(i).getType() + "|| Actually is: " +this.expectedOuput.get(i).getType());
            }
        }
        Double acc = testSet.size() - count;
        acc = acc / testSet.size();
        acc = acc * 100;
        System.out.println("accuracy = " + acc + "% " + (testSet.size() - count) + "/" +testSet.size());
    }

    public void compareInstances(){
        for(Instance test: this.testSet) {
            compareInstance(test);

        }
        //compareInstance(testSet.get(55));

    }

    public void compareInstance(Instance test) {
        //Comparator<Instance> comparator = new InstanceComparator();
        ArrayList<InstanceCount> closestInstances = new ArrayList<InstanceCount>();
        for(Instance trained : this.training) {
            InstanceCount inst = new InstanceCount(trained.getType(), getDistance(test, trained));
            closestInstances = setClosestInstances(inst , closestInstances);
        }
        test.setType(getType(closestInstances));
    }



    public ArrayList<InstanceCount> setClosestInstances(InstanceCount inst, ArrayList<InstanceCount> closestInstances){
        //need to know about k (how many items to compare too)
        int count = 0;
        //fill the closest distances collection first.
        if(closestInstances.size() < 3){
            closestInstances.add(inst);
            return closestInstances;
        }
        else{
            for(int i = 0; i < closestInstances.size(); i++){
                //System.out.println(count++);
                //System.out.println("this is the type here " + inst.getDistanceToUnseen());
                if(inst.getDistanceToUnseen() < closestInstances.get(i).getDistanceToUnseen()){
                    //  System.out.println("this is the type here " + inst.getType());
                    closestInstances.set(i, inst);
                    return closestInstances;
                }
            }

        }
        //if this distance is smaller than others.

        return closestInstances;
    }
    public double getDistance(Instance i, Instance i2){
        double distance;
        //Petal length att
        distance = getAttributeDistance(i.getPetalLength(),i2.getPetalLength(), pLrange);
        //Sepal length att
        distance = distance + getAttributeDistance(i.getSepalLength(),i2.getSepalLength(), sLrange);
        //Petal width att
        distance = distance + getAttributeDistance(i.getPetalWidth(),i2.getPetalWidth(), pWrange);
        //Sepal width att
        distance = distance + getAttributeDistance(i.getSepalWidth(),i2.getSepalWidth(), sWrange);
        //System.out.println(Math.sqrt(distance));
        return Math.sqrt(distance);
    }

    public double getAttributeDistance(double i, double i2, double r){
        return (i-i2)/r;
    }
    public Instance.classType getType(ArrayList<InstanceCount> finalClosestInstaces){
        int type1, type2, type3;
        type1 = type2 = type3 = 0;
        for (InstanceCount i :finalClosestInstaces){
            if (i.getType() == Instance.classType.setosa){
                type1 ++;
            }
            if (i.getType() == Instance.classType.virginica){
                type2 ++;
            }
            if (i.getType() == Instance.classType.veriscolor){
                type3 ++;
            }
        }
        //System.out.println(type1);
        if(type1 >= 2){
            return Instance.classType.setosa;
        }
        else if(type2 >= 2){
            return Instance.classType.virginica;
        }
        else if(type3 >= 2){
            return Instance.classType.veriscolor;
        }

        return Instance.classType.veriscolor;

    }

    public void getRange(){
        double slMin, plMin, pwMin,  swMin;
        slMin = plMin = plMin = pwMin = swMin = 1000;
        double slMax, plMax, pwMax, swMax;
        slMax = plMax = plMax = pwMax = swMax = 0;

        for(Instance i: this.training){
            if(i.getPetalLength() >= plMax){
                plMax = i.getPetalLength();
            }
            if(i.getPetalWidth() >= pwMax){
                pwMax = i.getPetalWidth();
            }
            if(i.getSepalLength() >= slMax){
                slMax = i.getSepalLength();
            }
            if(i.getSepalWidth() >= swMax){
                swMax = i.getSepalWidth();
            }

            //minimums
            if(i.getPetalLength() <= plMin){
                plMin = i.getPetalLength();
            }
            if(i.getPetalWidth() <= pwMin){
                pwMin = i.getPetalWidth();
            }
            if(i.getSepalLength() <= slMin){
                slMin = i.getSepalLength();
            }
            if(i.getSepalWidth() <= swMin){
                swMin = i.getSepalWidth();
            }
        }
        this.pLrange = plMax- plMin;
        this.pWrange = pwMax - pwMin;
        this.sLrange = slMax- slMin;
        this.sWrange = swMax - swMin;
        System.out.println("sepal length range: " + sLrange);
        System.out.println("sepal width range: "  + sWrange);
        System.out.println("petal length range: " + pLrange);
        System.out.println("petal width range: " + pWrange);
    }


    /**
     * this method makes an instance object of all the data entries in the data set
     * it then places them in the arrayList for training data and returns this list.
     */
    public ArrayList<Instance> makeInstances(File data, boolean training){
        Scanner sc;
        ArrayList<Instance> output = new ArrayList<Instance>();
        try {
            sc = new Scanner(data);
            while(sc.hasNext()){
                Instance i;
                if(training){
                    i = new Instance(sc.nextDouble(), sc.nextDouble(), sc.nextDouble(), sc.nextDouble(), sc.nextLine());
                }
                else{
                    i = new Instance(sc.nextDouble(), sc.nextDouble(), sc.nextDouble(), sc.nextDouble(), null);
                    sc.nextLine();
                }
                output.add(i);
            }
        }catch (FileNotFoundException e){}
        return output;
    }






}