package drs.QAP;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

//public void generateRandomQAPFile(int N, int range,String pathname)
//public void solveGeneticAlgorithm(int popSize,double percent,int generationCount)
//public void solveWisdomOfTheCrowds(int[][] crowd,int crowdsize,double expertPercent)
	
	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub

//Initialization 
		System.out.println("Starting...");
		QuadradicAssignment QAP = new QuadradicAssignment();
		String filename = "C:\\Users\\Daniel\\OneDrive\\Documents\\Fall 2017-DESKTOP-8KU0N3M\\AI\\Final Project\\esc128.txt";
		int iN = 128;
		//QAP.generateRandomQAPFile(20, 30, filename);
		QAP.readData(filename);
		
		
//Start timer
		long start = System.currentTimeMillis();

		
		
//Brute Force
		//QAP.solveBruteForce();
		//System.out.println(tst.getMinDistance());
		
//Wisdom Of The Crowds/GA
		int crowdSize = 10;
		int[][] crowd = new int[crowdSize][iN];
		int lowestGA = Integer.MAX_VALUE;
		int totalGA = 0;
		
	//Fill Crowd
		for(int i = 0; i < crowdSize; i++)
		{
			QAP.solveGeneticAlgorithm(1000,.8,1000);
			int[] tmp = QAP.getMinPath();
			int tempDis = QAP.getMinDistance();
			totalGA += tempDis;
			if(lowestGA > tempDis)
				lowestGA = tempDis;
			for(int k = 0;k < iN; k++)
				crowd[i][k] = tmp[k];
		}
		long end = System.currentTimeMillis();
	//Solve WOC
		QAP.solveWisdomOfTheCrowds(crowd, crowdSize,.0);
	//Print Results
		System.out.println("Minimum GA Distance: " + lowestGA);
		System.out.println("Average GA Distance: " + totalGA/crowdSize);
		System.out.println("WOC Distance: "+ QAP.getMinDistance());
		int[] lowerPerm = QAP.getMinPath();
		for(int j = 0; j < iN; j++)
		{
			System.out.print(lowerPerm[j] + " ");
		}
		System.out.println("");
		System.out.println("Average Time: "+((end - start))/crowdSize);
		
	}


}
