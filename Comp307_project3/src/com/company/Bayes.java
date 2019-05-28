package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOError;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Bayes {
    ArrayList<int[]> trainingInstances;
    ArrayList<int[]> unseenInstances = new ArrayList<>();
    File trainFile;
    File testFile;
    double[][] probabillityTable;
    double numberOfSpam;
    double numberOfNotSpam;
    ArrayList<int[]> spamInst;
    ArrayList<int[]> notSpamInst;


    public Bayes(String train , String test){
        this.trainFile = new File(train);
        this.testFile = new File(test);
        createInstances();
        splitInstances();
        initTable();
        for(int i = 0 ; i< 25; i++){
            System.out.println("-----------------------");
            System.out.println(i+"| "+  round(probabillityTable[0][i], 4) + "  |  " + round(probabillityTable[1][i], 4)+ " |");
        }
        classifyUnseen();
    }



    public void classifyUnseen(){
        createUnseenInstances();
        System.out.println();
        for (int i = 0; i < unseenInstances.size(); i++){
            String result;
            if(calculateProbabillity(unseenInstances.get(i)) == 1){
                result ="spam";
            }
            else {
                result = "not Spam";
            }
            System.out.println("instance " + (i) + ": is " + result);
        }
    }

    public int calculateProbabillity(int[] instance){
        double spamProb = probabillityTable[0][0];

        for (int i = 0; i < instance.length; i++){
            int idx = (i+1)*2;
            spamProb*=probabillityTable[0][idx-instance[i]];
        }

        double notSpamProb = probabillityTable[0][0];
        for (int i = 0; i < instance.length; i++){
            int idx = (i+1)*2;
            notSpamProb*=probabillityTable[1][idx-instance[i]];
        }
        System.out.println("spam prob = " + spamProb);
        System.out.println("not spam prob = " + notSpamProb);
        if (spamProb > notSpamProb){
            return 1;
        }
        else return 0;

    }



    public void initTable(){
        numberOfNotSpam = notSpamInst.size();
        numberOfSpam = spamInst.size();
        probabillityTable = new double[2][25];
        probabillityTable[0][0] = numberOfSpam;
        probabillityTable[1][0] = numberOfNotSpam;
        int attIndex = 0;
        for(int i = 1; i < 25; i ++){
            if(i % 2 != 0) {
                //odd do the att(x) = true probabillitlites for spam and !spam
                probabillityTable[0][i] = getTrueFeatureCount(attIndex, spamInst) / spamInst.size();
                probabillityTable[1][i] = getTrueFeatureCount(attIndex, notSpamInst) / notSpamInst.size();
            }
            else {
                //then do the att(x) = false probabillitles for spam and !spam
                probabillityTable[0][i] = getFalseFeatureCount(attIndex, spamInst) / spamInst.size();
                probabillityTable[1][i] = getFalseFeatureCount(attIndex, notSpamInst) / notSpamInst.size();
                attIndex++;
            }

        }
    }

    public void splitInstances(){
        spamInst = new ArrayList<>();
        notSpamInst = new ArrayList<>();
        for (int[] inst : trainingInstances){
            if(inst[12]== 1){
                spamInst.add(inst);
            }
            else{
                notSpamInst.add(inst);
            }
        }
    }


    public void createInstances() {
        this.trainingInstances = new ArrayList<>();
        try {
            Scanner sc = new Scanner(this.trainFile);
            while (sc.hasNextLine()) {
                int instance[] = new int[13];
                for (int i = 0; i < instance.length; i++) {
                    instance[i] = sc.nextInt();
                }
                this.trainingInstances.add(instance);
                sc.nextLine();
            }
        } catch (IOException i){}
    }

    public void createUnseenInstances() {
        this.unseenInstances = new ArrayList<>();
        try {
            Scanner sc = new Scanner(this.testFile);
            while (sc.hasNextLine()) {
                int instance[] = new int[12];
                for (int i = 0; i < instance.length; i++) {
                    instance[i] = sc.nextInt();
                }
                this.unseenInstances.add(instance);
                sc.nextLine();
            }
        } catch (IOException i){}
    }



    //counts the 1's in the instances based on the att index
    public double getTrueFeatureCount(int featureIndex, ArrayList<int[]> insts){
        double count = 0;
        for(int[] inst : insts){
            if(inst[featureIndex] == 1){
                count++;
            }
        }
        return count;
    }


    public double getFalseFeatureCount(int featureIndex, ArrayList<int[]> insts){
        double count =  getTrueFeatureCount(featureIndex,  insts);
        return insts.size() - count;
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }




}
