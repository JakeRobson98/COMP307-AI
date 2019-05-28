/*
 * This file is part of JGAP.
 *
 * JGAP offers a dual license model containing the LGPL as well as the MPL.
 *
 * For licensing information please see the file license.txt included with JGAP
 * or have a look at the top of class org.jgap.Chromosome which representatively
 * includes the JGAP license policy applicable for any file delivered with JGAP.
 */
package Comp307;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import org.jgap.*;
import org.jgap.gp.*;
import org.jgap.gp.function.*;
import org.jgap.gp.impl.*;
import org.jgap.gp.terminal.*;


public class Part3 extends GPProblem {
  /** String containing the CVS revision. Read out via reflection!*/
  private final static String CVS_REVISION = "$Revision: 1.25 $";

  private static Variable vx;
  private static Variable CT;
  private static Variable USz;
  private static Variable UShp;
  private static Variable MA;
  private static Variable SESz;
  private static Variable BN;
  private static Variable BC;
  private static Variable NN;
  private  static Variable M;
  
  static ArrayList<Instance> training = new ArrayList();
  static ArrayList<Instance> test = new ArrayList();
  static File ftraining;
  static File ftest;
  //static Float[] x = new Float[10]; 


  //protected static Float[] x = new Float[20];

//  protected static float[] y = new float[] {};

  public Part3(GPConfiguration a_conf)
      throws InvalidConfigurationException {
    super(a_conf);
  }

  /**
   * This method is used for setting up the commands and terminals that can be
   * used to solve the problem.
   * In this example an ADF (an automatically defined function) is used for
   * demonstration purpuses. Using an ADF is optional. If you want to use one,
   * care about the places marked with "ADF-relevant:" below. If you do not want
   * to use an ADF, please remove the below places (and reduce the outer size of
   * the arrays "types", "argTypes" and "nodeSets" to one).
   * Please notice, that the variables types, argTypes and nodeSets correspond
   * to each other: they have the same number of elements and the element at
   * the i'th index of each variable corresponds to the i'th index of the other
   * variables!
   *
   * @return GPGenotype
   * @throws InvalidConfigurationException
   */
  public GPGenotype create()
      throws InvalidConfigurationException {
    GPConfiguration conf = getGPConfiguration();
    // At first, we define the return type of the GP program.
    // ------------------------------------------------------
    Class[] types = {
        // Return type of result-producing chromosome
        CommandGene.IntegerClass,
        // ADF-relevant:
        // Return type of ADF 1
        CommandGene.IntegerClass};
    // Then, we define the arguments of the GP parts. Normally, only for ADF's
    // there is a specification here, otherwise it is empty as in first case.
    // -----------------------------------------------------------------------
    Class[][] argTypes = {
        // Arguments of result-producing chromosome: none
        {},
        // ADF-relevant:
        // Arguments of ADF1: all 3 are float
        {CommandGene.IntegerClass, CommandGene.IntegerClass, CommandGene.IntegerClass}
    };
    // Next, we define the set of available GP commands and terminals to use.
    // Please see package org.jgap.gp.function and org.jgap.gp.terminal
    // You can easily add commands and terminals of your own.
    // ----------------------------------------------------------------------
    CommandGene[][] nodeSets = { {
        // We use a variable that can be set in the fitness function.
        // ----------------------------------------------------------
       // vx = Variable.create(conf, "X", CommandGene.FloatClass),
        CT = Variable.create(conf, "CT", CommandGene.IntegerClass),
    	USz= Variable.create(conf, "USz", CommandGene.IntegerClass),
    	UShp= Variable.create(conf, "Ushp", CommandGene.IntegerClass),
    	MA = Variable.create(conf, "ma", CommandGene.IntegerClass),
    	SESz = Variable.create(conf, "sesz", CommandGene.IntegerClass),
    	BN = Variable.create(conf, "bn", CommandGene.IntegerClass),
    	BC= Variable.create(conf, "bc", CommandGene.IntegerClass) ,
    	NN = Variable.create(conf, "nn", CommandGene.IntegerClass),
    	M = Variable.create(conf, "m", CommandGene.IntegerClass),	
        new Multiply(conf, CommandGene.IntegerClass),
        //new Multiply3(conf, CommandGene.IntegerClass),
        new Divide(conf, CommandGene.IntegerClass),
        new Add(conf, CommandGene.IntegerClass),
        //new Sine(conf, CommandGene.FloatClass),
        //new Exp(conf, CommandGene.FloatClass),
        new Subtract(conf, CommandGene.IntegerClass),
       // new Pow(conf, CommandGene.IntegerClass),
        new Terminal(conf, CommandGene.IntegerClass, -5.0d, 5.0d, true),
        // ADF-relevant:
        // Construct a reference to the ADF defined in the second nodeset
        // which has index 1 (second parameter of ADF-constructor).
        // Furthermore, the ADF expects three input parameters (see argTypes[1])
        //
       // new ADF(conf, 1 , 3),
    },
        // ADF-relevant:
        // and now the definition of ADF(1)
        {
        new Add3(conf, CommandGene.IntegerClass),
        }
    };
    // Here, we define the expected (optimal) output we want to achieve by the
    // function/formula to evolve by the GP.
    // -----------------------------------------------------------------------
    Random random = new Random();
    // Randomly initialize function data (X-Y table) for x^4+x^3+x^2-x
    // ---------------------------------------------------------------
    
    // Create genotype with initial population. Here, we use the declarations
    // made above:
    // Use one result-producing chromosome (index 0) with return type float
    // (see types[0]), no argument (argTypes[0]) and several valid commands and
    // terminals (nodeSets[0]). Contained in the node set is an ADF at index 1
    // in the node set (as declared with the second parameter during
    // ADF-construction: new ADF(..,1,..)).
    // The ADF has return type float (types[1]), three input parameters of type
    // float (argTypes[1]) and exactly one function: Add3 (nodeSets[1]).
    // ------------------------------------------------------------------------
    return GPGenotype.randomInitialGenotype(conf, types, argTypes, nodeSets,
        20, true);
  }

  private static double testResult(IGPProgram allTimeBest){
	
		int correct = 0;
		for(Instance i : test){
			CT.set(i.getCT());
	        USz.set(i.getUSz());
	        UShp.set(i.UShp);
	        MA.set(i.getMA());
	        SESz.set(i.getSESz());
	        BN.set(i.getBN());
	        BC.set(i.getBN());
	        NN.set(i.getNN());
	        M.set(i.getM());
			int result = allTimeBest.execute_int(0, new Object[0]);
			int classification = 0;
			if(result < 0){
				classification = 4;
			}else{
				 classification = 2;
			}
			 if(classification == i.getClassType()){
				 correct++;
			 }
		}
		double accuracy = ((double)correct/(double) test.size())*100;
		System.out.println("number of correct classifications = " + correct + "/" + test.size());
		System.out.println("accuracy = " + accuracy);
		return accuracy;
	}
  
  
  
  
  
  
  public static void main(String[] args)
      throws Exception {
	  ftraining = new File(args[0]);
	  ftest = new File(args[1]);
	  initialiseTraining();
	  initialiseTest();
	  //ftest = new File(args[1]);
    // Setup the algorithm's parameters.
    // ---------------------------------
    GPConfiguration config = new GPConfiguration();
    // We use a delta fitness evaluator because we compute a defect rate, not
    // a point score!
    // ----------------------------------------------------------------------
    config.setGPFitnessEvaluator(new DefaultGPFitnessEvaluator());
    config.setMaxInitDepth(4);
    config.setPopulationSize(1000);
    config.setMaxCrossoverDepth(8);
    config.setFitnessFunction(new Part3.FormulaFitnessFunction());
    config.setStrictProgramCreation(true);
    config.setCrossoverProb(0.9f);
    config.setMutationProb(0.1f);
    config.setReproductionProb(0.1f);
    
    GPProblem problem = new Part3(config);
    
    GPGenotype gp = problem.create();
    gp.setVerboseOutput(true);
    // Start the computation with maximum 800 evolutions.
    // if a satisfying result is found (fitness value almost 0), JGAP stops
    // earlier automatically.
    // --------------------------------------------------------------------
    for(int i = 0; i <= 800; i ++) {
    	gp.evolve();
    	gp.calcFitness();
    	System.out.println("generation = " +  i);
    }
   
    // Print the best solution so far to the console.
    // ----------------------------------------------
    gp.outputSolution(gp.getAllTimeBest());
   
    double testResults = testResult(gp.getAllTimeBest());
   
    // Create a graphical tree of the best solution's program and write it to
    // a PNG file.
    // ----------------------------------------------------------------------
    problem.showTree(gp.getAllTimeBest(), "mathproblem_best.png");
  }

  private static void initialiseTraining() {
	  Scanner sc;
	  try {
		sc = new Scanner(ftraining);
	  while(sc.hasNextLine()) {
	    	training.add(new Instance(sc.nextInt(),sc.nextInt() ,sc.nextInt(), sc.nextInt(), sc.nextInt() ,sc.nextInt() ,sc.nextInt() ,sc.nextInt(), sc.nextInt(), sc.nextInt()));
	    	sc.nextLine();
	  }
	  } catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
  }
  private static void initialiseTest() {
	  Scanner sc;
	  try {
		sc = new Scanner(ftest);
	  while(sc.hasNextLine()) {
	    	test.add(new Instance(sc.nextInt(),sc.nextInt() ,sc.nextInt(), sc.nextInt(), sc.nextInt() ,sc.nextInt() ,sc.nextInt() ,sc.nextInt(), sc.nextInt(), sc.nextInt()));
	    	sc.nextLine();
	  }
	  } catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
  }

/**
   * Fitness function for evaluating the produced fomulas, represented as GP
   * programs. The fitness is computed by calculating the result (Y) of the
   * function/formula for integer inputs 0 to 20 (X). The sum of the differences
   * between expected Y and actual Y is the fitness, the lower the better (as
   * it is a defect rate here).
   */
  public static class FormulaFitnessFunction
      extends GPFitnessFunction {
    protected double evaluate(final IGPProgram a_subject) {
      return computeRawFitness(a_subject);
    }

    public static double computeRawFitness(final IGPProgram ind) {
      
      int correct = 0;
      for(Instance i: training) {
	    	 CT.set(i.getCT());
	    	 USz.set(i.getUSz());
	    	 UShp.set(i.getUShp());
	    	 MA.set(i.getMA());
	    	 SESz.set(i.getSESz());
	    	BN.set(i.getBN());
	    	BC.set(i.getBC());
	    	NN.set(i.getNN());
	    	M.set(i.getM());
	    	int classType = i.getClassType();
	            // Execute the GP program representing the function to be evolved.
	            // As in method create(), the return type is declared as float (see
	            // declaration of array "types").
	            // ----------------------------------------------------------------
	            int result = ind.execute_int(0, new Object[0]);
	           // System.out.println("result = " +  result);
	            int classification = 0; 
	           
	            // Sum up the error between actual and expected result to get a defect
	            // rate.
	            // -------------------------------------------------------------------
	            if(result < 0) {
	            	classification = 4;
	            }
	            
	            else  {
	            	classification = 2;
	            }
	            //System.out.println("claasification = " + classification + "actual type: " + classType);
	            if(classification == classType) {
	            	correct++;
	            	//System.out.println(correct);
	            }           
      }
      double accuracy = ((double)(correct)/(training.size())) * 100;
//      System.out.println("training size : " + training.size());
//      System.out.println("correct instances = " + correct);
//      System.out.println("acc = " + accuracy);
      return accuracy;
    }
  }
}
class Instance{
	public int getClassType() {
		return ClassType;
	}

	public void setClassType(int classType) {
		ClassType = classType;
	}

	int CT;
	int USz;
	int UShp;
	int MA;
	int SESz;
	int BN;
	int BC;
	int NN;
	int M;
	int ClassType;
	
	Instance(int ct, int usz, int ushp, int ma, int sesz, int bn, int bc, int nn, int m, int classType){
		this.CT = ct;
		this.USz = usz;
		this.UShp = ushp;
		this.MA = ma;
		this.SESz = sesz;
		this.BN = bn;
		this.BC = bc;
		this.NN= nn;
		this.M = m;
		this.ClassType = classType;
	}

	public int getCT() {
		return CT;
	}

	public void setCT(int cT) {
		CT = cT;
	}

	public int getUSz() {
		return USz;
	}

	public void setUSz(int uSz) {
		USz = uSz;
	}

	public int getUShp() {
		return UShp;
	}

	public void setUShp(int uShp) {
		UShp = uShp;
	}

	public int getMA() {
		return MA;
	}

	public void setMA(int mA) {
		MA = mA;
	}

	public int getSESz() {
		return SESz;
	}

	public void setSESz(int sESz) {
		SESz = sESz;
	}

	public int getBN() {
		return BN;
	}

	public void setBN(int bN) {
		BN = bN;
	}

	public int getBC() {
		return BC;
	}

	public void setBC(int bC) {
		BC = bC;
	}

	public int getNN() {
		return NN;
	}

	public void setNN(int nN) {
		NN = nN;
	}

	public int getM() {
		return M;
	}

	public void setM(int m) {
		M = m;
	}
	
}

