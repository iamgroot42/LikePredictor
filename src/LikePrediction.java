import java.util.ArrayList;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.types.Post;
import com.restfb.types.StatusMessage;
import com.restfb.types.User;

import Jama.Matrix;
import Jama.SingularValueDecomposition;

public class LikePrediction {
	
	/**
	  * The difference between 1 and the smallest exactly representable number
	  * greater than one. Gives an upper bound on the relative error due to
	  * rounding of floating point numbers.
	  */
	 public static double MACHEPS = 2E-16;

	 /**
	  * Updates MACHEPS for the executing machine.
	  */
	 public static void updateMacheps() {
	  MACHEPS = 1;
	  do
	   MACHEPS /= 2;
	  while (1 + MACHEPS / 2 != 1);
	 }

	 /**
	  * Computes the Moore–Penrose pseudoinverse using the SVD method.
	  * 
	  * Modified version of the original implementation by Kim van der Linde.
	  */
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
	 
	//6x1 feature vector for now
	//Offset,TimeOfDay,NumberOfFriends,PostLength,Emoticons,Hashtags
	static Matrix X;
	static Matrix Y;
	static Matrix Theta;
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
	
	public static void main(String args[])
	{
		String MY_ACCESS_TOKEN="CAACEdEose0cBAK1AnUr5RZA65ntNiav7tEkfpNcgb8pPKjxrI9H3CVHH6N5fNZCxIHcsZB9vEc5uymfP97idIMDe0CrpdfNh7DjLm2F6O2dYjFm1XdzUbPqpNZAzROZAbtj2IZBGlahCZAolUyquNHzLa4ZApNDWZCgXx6reoL0QTucp2hGB9DkqEs7vLxr8qdSFsWVrwZCEuStwZDZD";
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
		long f=facebookClient.fetchConnection("me/friends",User.class).getTotalCount();
		ArrayList<FVector> temp=new ArrayList<FVector>();;
		//Array of features:
		ArrayList<Double> why=new ArrayList<Double>();
		int i=0;
		for(StatusMessage x:statuses)
		{
			FVector useless=new FVector();
			why.add((double)x.getLikes().size());
			//System.out.println(x.getMessage()); //Status message
			int hh=x.getUpdatedTime().getHours();
			int mm=x.getUpdatedTime().getMinutes();
			int time=hh*60+mm;
			int s=x.getMessage().length();
			useless.setNumber_of_friends(f);
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
		//System.out.println("Theta matrix :");
		//Theta.print(6, 5);		
//		X.print(3, 2);
//		Theta.print(3, 2);
		Matrix prediction=(X.times(Theta));
		prediction.print(3, 5);
	}
}
