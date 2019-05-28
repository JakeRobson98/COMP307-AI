package part3;
import part2.DTmaker;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringJoiner;
import java.util.Random;

/**
 * Created by Jake on 15/04/18.
 */
public class Perceptron {
    private ArrayList<Image> trainingImages = new ArrayList();
    private ArrayList<Feature> features = new ArrayList(50);

    public Perceptron(String filename) {
        readData(new File(filename), null);
        Image img = trainingImages.get(0);
        this.features = createFeatures(img);

        for (Image i: trainingImages){
            System.out.println(i.toString());
            i.setFeatureValues(getFeatureValues(i, this.features));
        }



//        for (Feature f: features){
//            System.out.println(f.toString());
//        }
//        for (Image i: trainingImages){
//            System.out.println(i.toString());
//        }


//        for (Image i : trainingImages){
//            System.out.println(i.toString());
//        }
    }

    public void readData(File training, File test) {

        Scanner sc;
        try {
            sc = new Scanner(training);
            while (sc.hasNext()) {
                //throw away the first p1
                sc.nextLine();
                //get type
                String t = sc.next();
                char type = t.charAt(1);
                //get the width & height
                int width = sc.nextInt();
                int height = sc.nextInt();
                int[][] pixels = new int[width][height];
                sc.nextLine();
                String s = sc.nextLine();
                String s2 = sc.nextLine();
                String pixelString = s.concat(s2);
                int count = 0;
                for (int r = 0; r < height; r++) {
                    for (int c = 0; c < width; c++) {
                        pixels[r][c] = pixelString.charAt(count);
                        System.out.println("valss" + pixelString.charAt(count));
                        count++;
                    }
                }
                //dummy something
                Image i = new Image(width, height, pixels, type);

                //set the feature values for the new image.
                trainingImages.add(i);
            }
        } catch (FileNotFoundException e) {
            System.out.println("caked ir ");
        }
    }
    public ArrayList<Integer> getFeatureValues(Image image, ArrayList<Feature> features){
        ArrayList<Integer> output = new ArrayList<>(50);
        int count = 0;
        //System.out.println("features is empty " + features.isEmpty());
        for(Feature f : features){

            int sum=0;
            System.out.println(f.toString());
            System.out.println(image.toString());
            for(int i=0; i < 4; i++) {
//                System.out.println("pixel value " + image.pixels[f.rows[i]][f.cols[i]]);
//                System.out.println("f.rows  " + f.rows[i]);
//                System.out.println("f.rows  " + f.cols[i]);
                int y = f.rows[i];
                int x = f.cols[i];
                if(image.pixels[y][x] == f.vals[i]){
                    sum++;
                }
            }
            if (sum>=3){
                output.add(1);
            }
            else{
                output.add(0);
            }

            //System.out.println("sum= "+ sum);
        }
        //System.out.println( "output " + output.toString());
        return output;
    }

    public ArrayList<Feature> createFeatures(Image I) {
        ArrayList<Feature> output = new ArrayList<>();
        Feature feature;
        for (int f = 0; f < 50; f++) {
            int[] rows = new int[4];
            int[] cols = new int[4];
            int[] vals = new int[4];
            for (int i = 0; i < 4; i++) {
                Random r = new Random();
                rows[i] = r.nextInt(I.width);
                cols[i] = r.nextInt(I.height);
                vals[i] = r.nextBoolean() ? 1 : 0;
            }
            output.add(new Feature(rows, cols, vals));
        }

        return output;
    }
}
