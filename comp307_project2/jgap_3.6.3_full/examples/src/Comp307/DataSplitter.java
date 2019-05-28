package Comp307;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;


public class DataSplitter {
	private ArrayList<String[]> trainingSet = new ArrayList();
	private ArrayList<String[]> testSet = new ArrayList();
	private ArrayList<String[]> benign = new ArrayList();
	private ArrayList<String[]> mag = new ArrayList();
	
	public static void main(String[] args) {
		DataSplitter t = new DataSplitter(args[0]);
	}
	
	public DataSplitter(String args){
		try {
			File fname = new File (args);
			Scanner scan = new Scanner(fname);
			
			while(scan.hasNextLine()) {
				String line = scan.nextLine();
				
				String[] atts = line.split(",");
				for(int i = 0; i < atts.length; i++) {
					if(atts[i].equals("?")) {
						atts[i] = "-1";
						System.out.println(11111);
					}
				}
				if(atts[10].equals("4")){
					mag.add(atts);
				}
				else {
					benign.add(atts);
				}
			}scan.close();
			
			Collections.shuffle(mag);
			Collections.shuffle(benign);
			
			int totalInstances = mag.size() + benign.size();
			
			for(int i = 0; i < Math.round(mag.size()* 0.7); i++) {
				trainingSet.add(mag.get(i));
			}
			for(int i = 0; i < Math.round(benign.size()* 0.7); i++) {
				trainingSet.add(benign.get(i));
			}
			
			double index = Math.round(mag.size()* 0.7);
			for(int i = (int)index ; i < mag.size(); i ++) {
				testSet.add(mag.get(i));
			}
			index = Math.round(benign.size()* 0.7);
			for(int i = (int)index ; i < benign.size(); i ++) {
				testSet.add(benign.get(i));
			}
			System.out.println(this.testSet.size());
			System.out.println(this.trainingSet.size());
			
			FileWriter testFile = new FileWriter("testSet.txt");
			for(String[] s: testSet) {
				String ss = "";
				for(int i = 1; i < s.length; i ++) {
					ss = ss + s[i] + "\t";
				}
				testFile.write(ss + "\n");
			}
			
			
			FileWriter trainingFile = new FileWriter("trainingSet.txt");
			
			for(String[] s: trainingSet) {
				String ss = "";
				for(int i = 1; i < s.length; i ++) {
					ss = ss + s[i] + "\t";
				}
				trainingFile.write(ss+"\n");
			}
			
			testFile.close();
			trainingFile.close();
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
}

