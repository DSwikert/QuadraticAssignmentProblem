package drs.QAP;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

	
	
	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		QuadradicAssignment tst = new QuadradicAssignment();
		String filename = "filename";
		tst.generateRandomQAPFile(12, 10, filename);
		tst.readData(filename);
		tst.solveBruteForce();
		System.out.println(tst.getMinDistance());
		int[] finalperm = tst.getMinPath();
		for(int i = 0; i < 12;i++)
		{
			System.out.print(finalperm[i] + " ");
		}
		
	}

	

}
