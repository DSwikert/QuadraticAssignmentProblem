package drs.QAP;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

	
	
	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		QuadradicAssignment tst = new QuadradicAssignment();
		String filename = "filename";
//public void generateRandomQAPFile(int N, int range,String pathname)
		//tst.generateRandomQAPFile(20, 30, filename);
		tst.readData(filename);
		
		long start = System.currentTimeMillis();
//public void solveGeneticAlgorithm(int popSize,double percent,int generationCount)
		tst.solveGeneticAlgorithm(1000,.5,1000);
		//tst.solveBruteForce();
		//System.out.println(tst.getMinDistance());
		long end = System.currentTimeMillis();
		System.out.println((end - start)/1000);
        System.out.println("Done.");
		//int[] finalperm = tst.getMinPath();
		//for(int i = 0; i < 12;i++)
		//{
		//	System.out.print(finalperm[i] + " ");
		//}
		
	}


}
