package drs.QAP;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

	
	
	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		QuadradicAssignment QAP = new QuadradicAssignment();
		String filename = "C:\\Users\\Daniel\\OneDrive\\Documents\\Fall 2017-DESKTOP-8KU0N3M\\AI\\Final Project\\had12.txt";
		int iN = 12;
//public void generateRandomQAPFile(int N, int range,String pathname)
		//QAP.generateRandomQAPFile(20, 30, filename);
		QAP.readData(filename);
		
		
//Start timer
		long start = System.currentTimeMillis();

//Genetic Algorithm
//public void solveGeneticAlgorithm(int popSize,double percent,int generationCount)
		//QAP.solveGeneticAlgorithm(1000,.5,1000);
		
		
//Brute Force
		
		//QAP.solveBruteForce();
		//System.out.println(tst.getMinDistance());
		
//Wisdom Of The Crowds
//public void solveWisdomOfTheCrowds(int[][] crowd,int crowdsize)
		int[] starting = {1,2,3,4,5,6,7,8,9,10,11,12};
		int crowdSize = 100;
		int[][] crowd = new int[crowdSize][iN];
		int lowestGA = Integer.MAX_VALUE;
		int totalGA = 0;
		
		//crowd = QuadradicAssignment.createGeneticCrowd(QAP,20,.5,iN);
		
		for(int i = 0; i < crowdSize; i++)
		{
			QAP.solveGeneticAlgorithm(1000,.5,1000);
			int[] tmp = QAP.getMinPath();
			int tempDis = QAP.getMinDistance();
			totalGA += tempDis;
			if(lowestGA > tempDis)
				lowestGA = tempDis;
			for(int k = 0;k < iN; k++)
				crowd[i][k] = tmp[k];
		}
		
		
		QAP.solveWisdomOfTheCrowds(crowd, crowdSize,.0);
		System.out.println("Minimum GA Distance: " + lowestGA);
		System.out.println("Average GA Distance: " + totalGA/crowdSize);
		System.out.println("WOC Distance: "+ QAP.getMinDistance());
		int[] lowerPerm = QAP.getMinPath();
		for(int j = 0; j < iN; j++)
		{
			System.out.print(lowerPerm[j] + " ");
		}
		System.out.println("");
		
		
//Display timing results		
		long end = System.currentTimeMillis();
		System.out.println((end - start)/1000);
        System.out.println("Done.");
		
		
	}


}
