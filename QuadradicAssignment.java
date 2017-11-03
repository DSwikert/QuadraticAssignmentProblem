package drs.QAP;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;


public class QuadradicAssignment {
	//Declare
	private int iN;
	private int mDistance[][];
	private int mFlow[][];
	private int iMinDistance;
	private int[] aStartingList;
	private int[] aMinPermutation;
	
//Initialization Functions	
	//Initialize members needed for solving
	public void init(int count)
	{
		iN = count;
		mDistance = new int[iN][iN];
		mFlow = new int[iN][iN];
		aStartingList = new int[iN];
		aMinPermutation = new int[iN];
		System.out.println(iN);
	}
	//Read a txt file in specified format
	public void readData(String sFilename) throws FileNotFoundException
	{
		File fFile = new File(sFilename);
		Scanner scan = new Scanner(fFile);
		int iLineNum = 1;
		while(scan.hasNextLine())
		{
			if(iLineNum == 1)
			{
				String[] asLine = (scan.nextLine()).split(" ");
				int count = Integer.parseInt(asLine[0]);
				init(count);
			}
			else if(iLineNum > 2 && iLineNum < iN + 3)
			{
				String[] asLine = (scan.nextLine()).split("  ");
				for(int i = 0; i < iN;i++)
				{
					mDistance[iLineNum - 3][i] = Integer.parseInt(asLine[i]);
				}
			}
			else if(iLineNum > iN + 3)
			{
				String[] asLine = (scan.nextLine()).split("  ");
				for(int i = 0; i < iN;i++)
				{
					mFlow[iLineNum - (iN + 4)][i] = Integer.parseInt(asLine[i]);
				}
			}
			else
				scan.nextLine();
			iLineNum += 1;
				
		}
	}
	

//Utility Functions (i.e. getters/setters/print)
	
	public int[] getMinPath()
	{
		return aMinPermutation;
	}
	
	public int getMinDistance()
	{
		return iMinDistance;
	}
	
	public int getDistance(int[] list)
	{
		int iDistance = 0;		
		for(int i = 0;i < iN;i++)
		{
			for(int j = 0; j < iN;j++)
			{
				iDistance += mDistance[i][j] * mFlow[list[i]][list[j]]; 
			}
		}
		
		return iDistance;
	}
	//Prints a integer matrix
	public void printMatrix()
	{
		for(int i = 0;i < iN;i++)
		{
			for(int j = 0;j < iN;j++)
			{
				System.out.print(mDistance[i][j] + "  ");
			}
			System.out.println("\n");
		}
		System.out.println("\n");
		for(int i = 0;i < iN;i++)
		{
			for(int j = 0;j < iN;j++)
			{
				System.out.print(mFlow[i][j]);
			}
			System.out.println("\n");
		}
	}
	//Generate random matrixes for QAP with specified N and range
	public void generateRandomQAPFile(int N, int range,String pathname) throws FileNotFoundException
	{
		//Allocate matrixes
		int[][] distMatrix = new int[N][N];
		int[][] flowMatrix = new int[N][N];
		Random rand = new Random();
		
		
		for(int i = 0;i < N;i++)
		{
			for(int j = 0; j < N;j++)
			{
				//Only do half the matrix (since it is mirrored
				if(j <= i)
				{
					//Get random numbers
					int r1 = rand.nextInt(range);
					int r2 = rand.nextInt(range);
					//Half 0 in diagnal
					if(i == j)
					{
						distMatrix[i][j] = 0;
						flowMatrix[i][j] = 0;
					}
					//Add numbers to matrix
					else
					{
						distMatrix[i][j] = r1;
						distMatrix[j][i] = r1;
						
						flowMatrix[i][j] = r2;
						flowMatrix[j][i] = r2;
					}
				}
			}
		}
		
		
		//Write to file
		PrintWriter writer = new PrintWriter(pathname);
		for(int i = 0; i < (3 + (2 * N));i++)
		{
			if(i == 0)
				writer.println(N);
			else if(i > 1 && i < (N + 2))
			{
				for(int j = 0; j < N;j++)
				{
					writer.print(distMatrix[i - 2][j] + "  ");
				}
				writer.println("");
			}
			else if(i > (N + 2))
			{
				for(int j = 0; j < N;j++)
				{
					writer.print(flowMatrix[i - (N+3)][j] + "  ");
				}
				writer.println("");
			}
			else
				writer.println("");
		}
		writer.close();
		
		
	}
	
	
	
//Solving BruteForce Functions
	
	private void initBruteForce()
	{
		//Set starting list to be ordered permutation
		for(int i = 0;i < iN;i++)
		{
			aStartingList[i] = i;
		}
		//Set min distance as starting distance
		iMinDistance = getDistance(aStartingList);
	}
	
	//Function that gets called from public
	public void solveBruteForce()
	{
		initBruteForce();
		
		getOptimalPermutation(aStartingList,0,iN);
	}
	//Goes through every permutation and checks distance
	private void getOptimalPermutation(int[] permutation,int start,int end)
	{
		//If at the end of tree, check the distance
			if(start == end)
			{
				int tempDis = getDistance(permutation);
				if(tempDis < iMinDistance)
				{

					iMinDistance = tempDis;
					for(int j = 0; j < iN;j++)
					{
						aMinPermutation[j] = permutation[j];
					}
				}
			}
			else
			{
				for(int i = start; i < end;i++)
				{
		//Swap the two points
					swap(permutation,start,i);
		//Recursively call function 
					getOptimalPermutation(permutation, start+1,end);
		//Swap the points back to go to up tree level
					swap(permutation,start, i);
				}
			}
	}
	//Swap two integers in an array
	private void swap(int[] permutation,int i, int j)
	{
		int temp = permutation[i];
		permutation[i] = permutation[j];
		permutation[j] = temp;
	}
	
	
	
//Solving GA Functions
	int iPopulationSize;
	int[][] mPopulation;
	
	private void initGeneticAlgorithm(int popSize)
	{
		iPopulationSize = popSize;
		mPopulation = new int[iPopulationSize][iN];
		for(int i = 0;i < iN;i++)
		{
			aStartingList[i] = i;
		}
	}
	
	public void solveGeneticAlgorithm(int popSize)
	{
		initGeneticAlgorithm(popSize);
		setPopulation();
	}
	
	
	private void setPopulation()
	{
		for(int i = 0; i < iPopulationSize; i++)
		{
			mPopulation[i] = randomPermutation(aStartingList);
		}
	}
	
	private int[] randomPermutation(int[] org)
	{
		int[] temp = new int[iN];
		for(int i = 0; i < iN; i++)
		{
			temp[i] = org[i];
		}
		Random rand = new Random();
		for(int i = 0; i < iN;i++)
		{
			int rand_int = rand.nextInt(iN);
			swap(temp,i,rand_int);
		}
		return temp;
	}
	
	private void setRouletteWheel()
	{
		int totalDistance = 0;
		for(int i = 0; i < iPopulationSize;i++)
		{
			totalDistance += getDistance(mPopulation[i]);
		}
	}
	
	
	
	
	
}
