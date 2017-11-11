package drs.QAP;

import java.awt.*;

import javax.swing.*;


public class GUI extends JPanel{
	private int xMap = 1000;
	private int yMap = 750;
	private int iN;
	private double degree;
	private int line = 700;
	private int[] x;
	private int[] y;
	private int fontSize = 50;
	private int textOffset = 100;
	private int[] assignments;
	private boolean[][] connected;
	
	
	
	public GUI(int[] assign, boolean[][] connect,int N)
	{
		setBackground(Color.BLACK);
		setPreferredSize(new Dimension(2000,1500));
		iN = N;
		degree = 360/iN;
		x = new int[iN];
		y = new int[iN];
		assignments = new int[N];
		connected = new boolean[iN][iN];
		
		for(int i = 0; i < iN;i++)
		{
			assignments[i] = assign[i];
			x[i] = (int)(xMap + line * Math.cos(i * 2 * Math.PI/iN));
			y[i] = (int)(yMap + line * Math.sin(i * 2 * Math.PI/iN));
			for(int j = 0; j < iN; j++)
			{
				connected[i][j] = connect[i][j];
			}
		}
		
		
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D)g;
		g2.setColor(Color.BLUE);
		g2.setFont(new Font("TimesRoman", Font.BOLD, fontSize));
		
		g2.drawString("Location # (Facility #)", 0,40);
		
		
		
		//g.drawPolygon(x, y, iN);
		for(int i = 0; i < iN; i++)
		{
			if(x[i] > xMap)
				g2.drawString((i + 1) + "(" + assignments[i] + ")", x[i] + textOffset, y[i]);
			else
				g2.drawString((i + 1) + "(" + assignments[i] + ")", x[i] - (textOffset + fontSize), y[i]);
						
				
			g2.fillRect(x[i] - 2,y[i] - 2,5,5);
			for(int j = 0; j < iN; j++)
			{
				if(i != j && connected[assignments[i] - 1][assignments[j] - 1])
				{
					g2.drawLine(x[i], y[i], x[j], y[j]);
				}
			}
		}
		
	}
	
	
}
