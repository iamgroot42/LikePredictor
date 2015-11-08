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
	static Matrix X;
	static Matrix Y;
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


	//Offset,TimeOfDay,NumberOfFriends,PostLength,Emoticons,Hashtags
	private static void constructY(ArrayList<Double> y)
	{
		int i,size=y.size();
		double rekt[][]=new double[size][1];
		for(i=0;i<size;i++)
		{
			rekt[i][0]=y.get(i);
		}
		Y=new Matrix(rekt);
	}
	
	private static void constructX(ArrayList<FVector> x)
	{
		int i,n;
		n=x.size();
		double[][] ret=new double[n][6]; //Hard coding for now
		for(i=0;i<n;i++)
		{
			ret[i][0]=x.get(i).getOffset();
			ret[i][1]=x.get(i).getTime_of_day();
			ret[i][2]=x.get(i).getNumber_of_friends();
			ret[i][3]=x.get(i).getPost_length();
			ret[i][4]=x.get(i).getEmoticons();
			ret[i][5]=x.get(i).getHashtags();
		}
		X=new Matrix(ret);
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
			i++;
			//Hashtag and emoticon pre-processing for some later time
			//System.out.println(x.getPlace()); //Check presence for location tag ; optional
			//Process message to check emoticons, length of message, hashtags 
		}
		//Constructing vector 'Y'
		constructY(why);
		//Constructing matrix 'X'
		constructX(temp);
				
		Matrix X_transpose=X.transpose();
		Matrix sampletin=X_transpose.times(X);
		Matrix sampletinv=pinv(sampletin);
		Matrix tempu=sampletinv.times(X_transpose);
		Theta=tempu.times(Y);
		System.out.println("Training complete!");
		//System.out.println("Theta matrix :");
		//Theta.print(6, 5);		
//		X.print(3, 2);
//		Theta.print(3, 2);
	}

	public static void main(String args[])
	{
		Scanner in=new Scanner(System.in);
		String at="";
		System.out.println("Enter access token");
		at=in.nextLine();
		//Training predictor :
		Train(at);
		String input="";
		System.out.println("Enter post");
		input=in.nextLine();
		int hh=14;
		int mm=50;
		int time=hh*60+mm;
		double[][] ret=new double[1][6]; //Hard coding for now
		ret[0][0]=1;
		ret[0][1]=time;
		ret[0][2]=Number_of_friends;
		ret[0][3]=input.length();
		ret[0][4]=0;
		ret[0][5]=0;
		Matrix X_test=new Matrix(ret);
		System.out.println("Prediction : ");
		Matrix prediction=(X_test.times(Theta));
		prediction.print(1, 4);
	}
}
