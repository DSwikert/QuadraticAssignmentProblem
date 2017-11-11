package drs.QAP;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JFrame;

public class Main {

//public void generateRandomQAPFile(int N, int range,String pathname)
//public void solveGeneticAlgorithm(int popSize,double percent,int generationCount)
//public void solveWisdomOfTheCrowds(int[][] crowd,int crowdsize,double expertPercent)
	
	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		
	
		

//Initialization 
		System.out.println("Starting...");
		QuadradicAssignment QAP = new QuadradicAssignment();
		String filename = "C:\\Users\\Daniel\\OneDrive\\Documents\\Fall 2017-DESKTOP-8KU0N3M\\AI\\Final Project\\Data Files\\esc32h.txt";
		//QAP.generateRandomQAPFile(20, 30, filename);
		QAP.readData(filename);
		
		
//Start timer
		long start = System.currentTimeMillis();

		
		
//Brute Force
		//QAP.solveBruteForce();
		//System.out.println(tst.getMinDistance());
		
//Wisdom Of The Crowds/GA
		int GARunSize = 1;
		int lowestGA = Integer.MAX_VALUE;
		int totalGA = 0;
		int[] minAssignment = new int[QAP.getN()];
		
	//Fill Crowd
		for(int i = 0; i < GARunSize; i++)
		{
			QAP.solveGeneticAlgorithm(5000,.5,1000,false);
			int tempDis = QAP.getMinDistance();
			totalGA += tempDis;
			int[] tmpAssignment = QAP.getMinPath();
			if(lowestGA > tempDis)
			{
				lowestGA = tempDis;
				for(int j = 0; j < QAP.getN(); j++)
				{
					minAssignment[j] = tmpAssignment[j];
				}
			}
		}
		long end = System.currentTimeMillis();
	
		
		
	//Print Results
		System.out.println("\n\n\n Results");
		System.out.println("Minimum GA Distance: " + lowestGA);
		System.out.println("Average GA Distance: " + totalGA/GARunSize);
		System.out.println("");
		System.out.println("Average Time: "+((end - start))/GARunSize);
	
		QAP.showAssignments(minAssignment);
     
	}


}
