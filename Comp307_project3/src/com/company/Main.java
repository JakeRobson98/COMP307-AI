package com.company;

public class Main {

    public static void main(String[] args) {
        String TrainingFilename = args[0];
        String TestingFilename = args[1];
        new Bayes(TrainingFilename, TestingFilename);
    }
}
