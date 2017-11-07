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
		//System.out.println(iN);
		
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
				
				int count = scan.nextInt();
				init(count);
			}
			else if(iLineNum > 2 && iLineNum < iN + 3)
			{
				for(int i = 0; i < iN;i++)
				{
					mDistance[iLineNum - 3][i] = scan.nextInt();
				}
			}
			else if(iLineNum > iN + 3 && iLineNum < (4 + (iN * 2)))
			{
				for(int i = 0; i < iN;i++)
				{
					mFlow[iLineNum - (iN + 4)][i] = scan.nextInt();
				}
			}
			else
				scan.nextLine();
			iLineNum += 1;
				
		}
		
	}
	

//Utility Functions (i.e. getters/setters/print)
	
	private boolean isIn(int[] list, int pt)
	{
		boolean isIn = false;
		for(int i = 0; i < iN;i++)
		{
			if(list[i] == pt)
			{
				isIn = true;
			}
		}
		return isIn;
	}
	
	private void printArray(int[] arr, int arrSize)
	{
		for(int i = 0; i < arrSize; i++)
		{
			System.out.print(arr[i] + " ");
		}
		System.out.println("");
	}
	
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
				iDistance += mDistance[i][j] * mFlow[list[i] - 1][list[j] - 1]; 
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
		for(int i = 1;i < iN + 1;i++)
		{
			aStartingList[i - 1] = i;
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
	double[] aWheel;
	int iParentCount;
	int[] aParents;
	int[][] mChildren;
	
	
	private void initGeneticAlgorithm(int popSize, double percent)
	{
		iPopulationSize = popSize;
		iParentCount = (int)(percent * iPopulationSize);
		if(iParentCount % 2 != 0)
			iParentCount += 1;
		aParents = new int[iParentCount];
		aWheel = new double[iPopulationSize + 1];
		mPopulation = new int[iPopulationSize][iN];
		mChildren = new int[iParentCount][iN];
		for(int i = 1;i < iN + 1;i++)
		{
			aStartingList[i - 1] = i;
		}
	}
	
	public void solveGeneticAlgorithm(int popSize,double percent,int generationCount)
	{
		initGeneticAlgorithm(popSize,percent);
		setPopulation();
		System.out.println("Parent Count: "+iParentCount);
		int minDis;
		int maxDis;
		for(int i = 0; i < generationCount;i++)
		{
			sortPopulation(mPopulation,0,iPopulationSize - 1);
			minDis = getDistance(mPopulation[0]);
			maxDis = getDistance(mPopulation[iPopulationSize - 1]);
			if(i%100 == 0)
			{
				System.out.println("Distance of lowest: "+minDis);
				System.out.println("Distance of Highest: "+maxDis);
			}
			if(minDis == maxDis)
			{
				System.out.println("Distance of lowest: "+minDis);
				System.out.println("Distance of Highest: "+maxDis);
				iMinDistance = minDis;
				for(int j = 0; j < iN; j++)
					aMinPermutation[j] = mPopulation[0][j];
				printArray(aMinPermutation,iN);
				break;
			}
			setRouletteWheel();
			getParents();
			reproduction();
			
		}
	}
	
	public static int[][] createGeneticCrowd(QuadradicAssignment QAP,int runSize, double crowdPercentage,int N)
	{
		int QAPCrowdSize = (int)(crowdPercentage * runSize);
		int[][] QAPCrowd = new int[QAPCrowdSize][N];
		int[][] run = new int[runSize][N];
		for(int i = 0; i < runSize; i++)
		{
			QAP.solveGeneticAlgorithm(1000,.5,1000);
			int[] tmp = new int[N];
			tmp = QAP.getMinPath();
			for(int k = 0;k < N; k++)
				run[i][k] = tmp[k];
		}
		QAP.sortPopulation(run, 0, runSize - 1);
		for(int j = 0; j < QAPCrowdSize; j++)
		{
			QAPCrowd[j] = run[j];
		}
		return QAPCrowd;
		
	}
	
	private void printPop()
	{
		for(int j = 0; j < iPopulationSize; j++)
		{
			for(int k = 0; k < iN; k++)
			{
				System.out.print(mPopulation[j][k] + " ");
			}
			System.out.println("");
		}
		System.out.println("");
	}
	private void reproduction()
	{
		for(int i = 0; i < iParentCount;i += 2)
		{
			//child1 = mChildren[i];
			//child2 = mChildren[i+1];
			int[] child1 = new int[iN];
			int[] child2 = new int[iN];
			Random rand = new Random();
			int index = rand.nextInt(iN);
			for(int j = 0;j <= index;j++)
			{	
				child1[j] = mPopulation[aParents[i]][j];
				child2[j] = mPopulation[aParents[i+1]][j];
			}
			int firstCount = index + 1;
			int firstIndex = index + 1;
			int secondCount = index + 1;
			int secondIndex = index + 1;
			while(firstCount != iN && firstIndex < ((iN*2) - 1))
			{
				if(firstIndex < iN)
				{
					int f = mPopulation[aParents[i+1]][firstIndex];
					if(isIn(child1,f) == false)
					{
						child1[firstCount] = f;
						firstCount++;
					}
				}
				else
				{
					int f = mPopulation[aParents[i+1]][firstIndex - iN];
					if(isIn(child1,f) == false)
					{
						child1[firstCount] = f;
						firstCount++;
					}
				}
				firstIndex++;
			}
			while(secondCount != iN && secondIndex < ((iN*2) - 1))
			{
				if(secondIndex < iN)
				{
					int f = mPopulation[aParents[i]][secondIndex];
					if(isIn(child2,f) == false)
					{
						child2[secondCount] = f;
						secondCount++;
					}
				}
				else
				{
					int f = mPopulation[aParents[i]][secondIndex - iN];
					if(isIn(child2,f) == false)
					{
						child2[secondCount] = f;
						secondCount++;
					}
				}
				secondIndex++;
			}
				
				
			mChildren[i] = child1;
			mChildren[i+1] = child2;
			
		}
		
		for(int x = 0; x < iParentCount;x++)
		{
			for(int x1 = 0; x1 < iN; x1++)
			{
				mPopulation[(iPopulationSize - 1) - x][x1] = mChildren[x][x1];
			}
		}
	}
	
	private void setPopulation()
	{
		for(int i = 0; i < iPopulationSize; i++)
		{
			mPopulation[i] = randomPermutation(aStartingList);
		}
	}
	
	public int[] randomPermutation(int[] org)
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
		double totalDistance = 0;
		for(int i = 0; i < iPopulationSize;i++)
		{
			totalDistance += getDistance(mPopulation[i]);
		}
		aWheel[0] = 0;
		for(int j = 0; j < iPopulationSize + 1;j++)
		{
			if(j == 0)
				aWheel[j] = 0;
			else
			{
				double dis = getDistance(mPopulation[(iPopulationSize) - j])/totalDistance;
				aWheel[j] = dis + aWheel[j-1];
			}
		}
	}
	
	
	private void getParents()
	{
		
		Random rand = new Random();
		for(int i = 0; i < iParentCount;i++)
		{
			
			double dProbability = rand.nextDouble();
			for(int j = 0; j < iPopulationSize; j++)
			{
				if(dProbability > aWheel[j] && dProbability < aWheel[j + 1])
				{
					aParents[i] = j;
				}
			}
		
		}
	}
	
	
	//Merge Sort
		public void sortPopulation(int[][] pop, int l, int h)
		{
			int middle_index;
			if(l < h)
			{
				middle_index = (l+h)/2;
				sortPopulation(pop,l, middle_index);
				sortPopulation(pop,middle_index+1,h);
				merge(pop,l,middle_index,h);
			}
		}
		
		private void merge(int[][] pop, int lo, int middle, int h)
		{
			int size1 = (middle - lo) + 1;
			int size2 = h - middle;
			
			int[][] p1 = new int[size1][iN];
			int[][] p2 = new int[size2][iN];
			
			for(int x = 0;x <size1;x++)
				p1[x] = pop[lo+x];
			for(int y = 0; y < size2;y++)
				p2[y] = pop[middle+1 + y];
			
			
			int i = 0;
			int j = 0;
			int hld = lo;
			while(i < size1 && j < size2)
			{
				if(getDistance(p1[i]) <= getDistance(p2[j]))
				{
					pop[hld] = p1[i];
					i++;
				}
				else
				{
					pop[hld] = p2[j];
					j++;
				}
				hld++;
			}
			while(i < size1)
			{
				pop[hld] = p1[i];
				hld++;
				i++;
			}
			while(j < size2)
			{
				pop[hld] = p2[j];
				hld++;
				j++;
			}
			
		}
	
	
//Wisdom of the Crowds
	private int[][] wisdomChart;
	private int crowdSize;
	int[] WOC;
	
	private void initWOC(int crowdsize)
	{
		wisdomChart = new int[iN][iN];
		crowdSize = crowdsize;
		WOC = new int[iN];
	}
	public void solveWisdomOfTheCrowds(int[][] crowd,int crowdsize)
	{
		initWOC(crowdsize);
		setWisdom(crowd);
		
		for(int x = 0; x < iN;x++)
		{
			for(int y = 0; y < iN; y++)
			{
				System.out.print(wisdomChart[x][y] + " ");
			}
			System.out.println("");
		}
		
		fillWOC();
		
		aMinPermutation = WOC;
		iMinDistance = getDistance(WOC);
	}
	private void fillWOC()
	{
		for(int i = 0; i < iN;i++)
		{
			//System.out.println("Index: "+i+" Max: "+getMax(i));
			WOC[i] = getMax(i);
			
		}
	}
	private int getMax(int index)
	{
		int maxInIndex = 0;
		int maxValue = 0;
		for(int i = 0; i < iN;i++)
		{
			int tmp = wisdomChart[index][i];
			if(tmp > maxValue)
			{
				if(isIn(WOC,(i + 1)) == false)
				{
					maxValue = tmp;
					maxInIndex = i;
					System.out.println("Index: "+index+" Max: "+maxInIndex);
				}
			}
		}
		
		return maxInIndex + 1;
	}
	
	
	private void setWisdom(int[][] crowd)
	{	
		for(int i = 0; i < crowdSize;i++)
		{
			for(int j = 0; j < iN; j++)
			{
				wisdomChart[j][crowd[i][j] - 1] += 1;
			}
		}
	}
}
