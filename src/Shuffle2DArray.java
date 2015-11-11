//@author : Anshuman Suri - 2014021
//@author : Satyam Kumar - 2014096

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//Class to shuffle a 2D array (in terms of rows)
public class Shuffle2DArray {
	private static List<Integer> shuf;
	public static double[][] shuffleX(double[][] x,int n)
	{
		int i,j;
		double[][] ret=new double[n][11]; //Hard coded 11
		shuf=new ArrayList<Integer>();
		for(i=0;i<n;i++)
		{
			shuf.add(i);
		}
		i=0;
		Collections.shuffle(shuf);
		for(Integer y:shuf)
		{
			for(j=0;j<11;j++) //Hard coded 11
			{
				ret[i][j]=x[y][j];
			}
			i++;
		}
		return ret.clone();
	}
	
	//Same shuffle order to maintain 1-1 ordering b/w prediction vector and actual vector
	public static double[][] shuffleY(double[][] x,int n)
	{
		int i;
		double[][] ret=new double[n][1]; 
		i=0;
		for(Integer y:shuf)
		{
			ret[i][0]=x[y][0];
			i++;
		}
		return ret.clone();
	}
}
