import java.util.ArrayList;
import java.util.Scanner;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.types.Post;
import com.restfb.types.StatusMessage;
import com.restfb.types.User;

import Jama.Matrix;
import Jama.SingularValueDecomposition;

public class LikePrediction {

	//Code for calculating pseudo-inverse (for non-invertible matrices) using SVD taken from
	// http://the-lost-beauty.blogspot.in/2009/04/moore-penrose-pseudoinverse-in-jama.html
	// Please don't consider the following segment while checking for plagiarism 
	
	public static double MACHEPS = 2E-16;
	static Matrix X_train;
	static Matrix X_test;
	static Matrix Y_train;
	static Matrix Y_test;
	static Matrix Theta;
	static long Number_of_friends;
	//Inverse Code Starts
	public static void updateMacheps() {
	  MACHEPS = 1;
	  do
	   MACHEPS /= 2;
	  while (1 + MACHEPS / 2 != 1);
	 }

	 public static Matrix pinv(Matrix x) {
	  int rows = x.getRowDimension();
	  int cols = x.getColumnDimension();
	  if (rows < cols) {
	   Matrix result = pinv(x.transpose());
	   if (result != null)
	    result = result.transpose();
	   return result;
	  }
	  SingularValueDecomposition svdX = new SingularValueDecomposition(x);
	  if (svdX.rank() < 1)
	   return null;
	  double[] singularValues = svdX.getSingularValues();
	  double tol = Math.max(rows, cols) * singularValues[0] * MACHEPS;
	  double[] singularValueReciprocals = new double[singularValues.length];
	  for (int i = 0; i < singularValues.length; i++)
	   if (Math.abs(singularValues[i]) >= tol)
	    singularValueReciprocals[i] =  1.0 / singularValues[i];
	  double[][] u = svdX.getU().getArray();
	  double[][] v = svdX.getV().getArray();
	  int min = Math.min(cols, u[0].length);
	  double[][] inverse = new double[cols][rows];
	  for (int i = 0; i < cols; i++)
	   for (int j = 0; j < u.length; j++)
	    for (int k = 0; k < min; k++)
	     inverse[i][j] += v[i][k] * singularValueReciprocals[k] * u[j][k];
	  return new Matrix(inverse);
	 }
	 //Inverse Code Ends

	//Offset,TimeOfDay,NumberOfFriends,PostLength,Emoticons,Hashtags,NumberOfComments,Place
	private static void constructY(ArrayList<Double> y)
	{
		int i,c,size=y.size();
		double rekt[][]=new double[size][1];
		for(i=0;i<size;i++)
		{
			rekt[i][0]=y.get(i);
		}
		double real_deal[][]=Shuffle2DArray.shuffleY(rekt, size); 
		Matrix chloro=new Matrix(real_deal);
		c=(6*size)/10;
		c=size-1;
		Y_train=chloro.getMatrix(0,c,0,0); //60%
//		Y_test=chloro.getMatrix(c+1,size-1,0,0); //40%
	}
	
	private static void setXandTest(ArrayList<FVector> x)
	{
		Matrix chloro;
		int i,n,s;
		n=x.size();
		double[][] ret=new double[n][8]; //Hard coding for now
		double real_deal[][];
		for(i=0;i<n;i++)
		{
			ret[i][0]=x.get(i).getOffset();
			ret[i][1]=x.get(i).getTime_of_day();
			ret[i][2]=x.get(i).getNumber_of_friends();
			ret[i][3]=x.get(i).getPost_length();
			ret[i][4]=x.get(i).getEmoticons();
			ret[i][5]=x.get(i).getHashtags();
			ret[i][6]=x.get(i).getNumber_of_comments();
			ret[i][7]=x.get(i).getPlace();
		}
		//Shuffling array to remove any bias
		real_deal=Shuffle2DArray.shuffleX(ret,n);
		s=(6*n)/10;
		s=n-1;
		chloro=new Matrix(real_deal);
		X_train=chloro.getMatrix(0,s,0,7); //60% 
//		X_test=chloro.getMatrix(s+1,n-1,0,7); //40%
	}
	
	private static double[] constructFeatures(FVector x)
	{
		//Accepts status-message object and constructs features for that example
		double[] ret=new double[8];
		ret[0]=x.getOffset();
		ret[1]=x.getTime_of_day();
		ret[2]=x.getNumber_of_friends();
		ret[3]=x.getPost_length();
		ret[4]=x.getEmoticons();
		ret[5]=x.getHashtags();
		ret[6]=x.getNumber_of_comments();
		ret[7]=x.getPlace();
		return ret.clone();
	}
	
	private static void Train(String MY_ACCESS_TOKEN)
	{
		FacebookClient facebookClient = new DefaultFacebookClient(MY_ACCESS_TOKEN);
		Connection<Post> pageFeed = facebookClient.fetchConnection("me/feed",Post.class);
		ArrayList<StatusMessage> statuses=new ArrayList<StatusMessage>();
		for(Post i:pageFeed.getData())
		{
			String idee=i.getId();
			if(i.getType().equals("status"))
			{
				try
				{
					StatusMessage user = facebookClient.fetchObject(idee, StatusMessage.class);
					statuses.add(user);
				}
				catch(Exception e){}
			}
		}
		Number_of_friends=facebookClient.fetchConnection("me/friends",User.class).getTotalCount();
		ArrayList<FVector> temp=new ArrayList<FVector>();;
		//Array of features:
		ArrayList<Double> why=new ArrayList<Double>();
		int i=0;
		for(StatusMessage x:statuses)
		{
			FVector useless=new FVector();
			why.add((double)x.getLikes().size());
			int hh=x.getUpdatedTime().getHours();
			int mm=x.getUpdatedTime().getMinutes();
			int time=hh*60+mm;
			int s=x.getMessage().length();
			useless.setNumber_of_friends(Number_of_friends);
			useless.setTime_of_day(time);
			useless.setPost_length(s);
			temp.add(useless);
			useless.setNumber_of_comments(x.getComments().size());
			if(x.getPlace()!=null) useless.setPlace(1);
			i++;
			//Hashtag and emoticon pre-processing for some later time 
		}
		//Constructing matrix 'X'
		setXandTest(temp);
		//Constructing vector 'Y'
		constructY(why);
				
		Matrix X_transpose=X_train.transpose();
		Matrix sampletin=X_transpose.times(X_train);
		Matrix sampletinv=pinv(sampletin);
		Matrix tempu=sampletinv.times(X_transpose);
		Theta=tempu.times(Y_train);
		System.out.println("Training complete!");
	}

	public static void main(String args[])
	{
		Scanner in=new Scanner(System.in);
		String at="";
		System.out.println("Enter access token");
		at=in.nextLine();
		in.close();
		//Training predictor :
		System.out.println("Training...");
		Train(at);
		//prediction.print(3, 2);
		Matrix prediction=X_train.times(Theta);
		double[][] pred,act;
		pred=prediction.getArray();
		act=Y_train.getArray();
		System.out.println("Predicted | Actual");
		int i,n;
		n=Y_train.getRowDimension();
		double error=0;
		for(i=0;i<n;i++)
		{
			if(pred[i][0]<0) pred[i][0]=0;
			System.out.println((int)pred[i][0]+"           "+(int)act[i][0]);
			error+=((double)((int)pred[i][0]-(int)act[i][0]))*((double)((int)pred[i][0]-(int)act[i][0]));
		}
		error/=n;
		System.out.println("Training error : "+error);
		//Hard coded training set as 100% of data
	}
}
