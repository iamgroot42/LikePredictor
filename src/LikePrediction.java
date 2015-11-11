//@author : Anshuman Suri - 2014021
//@author : Satyam Kumar - 2014096

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.types.Likes;
import com.restfb.types.NamedFacebookType;
import com.restfb.types.Post;
import com.restfb.types.User;

import Jama.Matrix;
import Jama.SingularValueDecomposition;

public class LikePrediction {

	//Code for calculating pseudo-inverse (for non-invertible matrices) using SVD taken from
	// http://the-lost-beauty.blogspot.in/2009/04/moore-penrose-pseudoinverse-in-jama.html
	// Please don't consider the following segment while checking for plagiarism 
	
	public static double MACHEPS = 2E-16;
	private static Matrix X_train;
	private	static Matrix X_test;
	private static Matrix Y_train;
	private static Matrix Y_test;
	private static Matrix Theta;
	private static long Number_of_friends;
	private static FacebookClient facebookClient;
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
		c=size-1; //change later
		Y_train=chloro.getMatrix(0,c,0,0); //60%
//		Y_test=chloro.getMatrix(c+1,size-1,0,0); //40%
	}
	
	private static void setXandTest(ArrayList<FVector> x)
	{
		Matrix chloro;
		int i,n,s;
		n=x.size();
		double[][] ret=new double[n][11]; //Hard coding for now
		double real_deal[][];
		for(i=0;i<n;i++)
		{
			ret[i][0]=x.get(i).getOffset();
			ret[i][1]=x.get(i).getAttachments();
			ret[i][2]=x.get(i).getComments();
			ret[i][3]=x.get(i).getPlace();
			ret[i][4]=x.get(i).getTime_of_day();
			ret[i][5]=x.get(i).getPost_length();
			ret[i][6]=x.get(i).getTags();
			ret[i][7]=x.get(i).getPicture();
			ret[i][8]=x.get(i).getShares();
			ret[i][9]=x.get(i).getWith_tags();
			ret[i][10]=x.get(i).getNumber_of_friends();
		}
		//Shuffling array to remove any bias
		real_deal=Shuffle2DArray.shuffleX(ret,n);
		s=(6*n)/10;
		s=n-1; //change later
		chloro=new Matrix(real_deal);
		X_train=chloro.getMatrix(0,s,0,10); //60% 
//		X_test=chloro.getMatrix(s+1,n-1,0,10); //40%
	}
	
	private static void Train()
	{		
		System.out.println("Training...");
		ArrayList<Post> statuses=new ArrayList<Post>();
		Connection<Post> pageFeed = facebookClient.fetchConnection("me/posts",Post.class,Parameter.with("limit", 100));
		for(Post i:pageFeed.getData())
		{
			String idee=i.getId();
			Post post = facebookClient.fetchObject(idee,
					  Post.class,
					  Parameter.with("fields", "likes.summary(true),comments.summary(true),type,with_tags,updated_time,shares,place,picture,message_tags,message,link"));
			System.out.println(post.getType());
			statuses.add(post);
		}
		Number_of_friends=facebookClient.fetchConnection("me/friends",User.class).getTotalCount();
		ArrayList<FVector> temp=new ArrayList<FVector>();;
		//Array of features:
		ArrayList<Double> why=new ArrayList<Double>();
		int i=0;
		for(Post x:statuses)
		{
			FVector useless=new FVector();
			if(x.getLikesCount()!=null)
			{
				if(x.getLikes().getData()!=null) WhoWillLike.addLike(x.getLikes().getData());
				why.add((double)x.getLikesCount());
			}
			else
			{
				why.add((double)0);
			}
			//Remove this feature :
			if(x.getAttachments()!=null)
			{
				useless.setAttachments(x.getAttachments().getData().size());
			}
			
			if(x.getCommentsCount()!=null)
			{
				useless.setComments(x.getCommentsCount());
			}
			
			if(x.getPlace()!=null)
			{
				useless.setPlace(1);
			}		
			
			int hh,mm;
			hh=x.getUpdatedTime().getHours();
			mm=x.getUpdatedTime().getMinutes();
			int time=hh*60+mm;
			useless.setTime_of_day(time);
			int s=0;
			
			if(x.getMessage()!=null)
			{
				s=x.getMessage().length();
			}
			
			useless.setPost_length(s);
			if(x.getMessageTags()!=null)
			{
				useless.setTags(x.getMessageTags().size());
			}
			
			if(x.getPicture()!=null)
			{
				useless.setPicture(1);
			}
			
			if(x.getSharesCount()!=null)
			{
				useless.setShares(x.getSharesCount());
			}
			
			if(x.getWithTags()!=null)
			{
				useless.setWith_tags(x.getWithTags().size());
			}
			
			useless.setNumber_of_friends(Number_of_friends);
			
			temp.add(useless);
			i++;
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

	public static void main(String[] args)
	{
		Scanner in=new Scanner(System.in);
		String at="";
		System.out.println("Enter access token");
		at=in.nextLine();
		in.close();
		facebookClient = new DefaultFacebookClient(at);
		System.out.println("Hi, "+facebookClient.fetchObject("me", User.class).getName());
		//Training predictor :
		Train();
		//prediction.print(3, 2);
		Matrix prediction=X_train.times(Theta);
		double[][] pred,act;
		pred=prediction.getArray();
		act=Y_train.getArray();
		int i,n;
		n=Y_train.getRowDimension();
		double error=0;
		double count=0;
		for(i=0;i<n;i++)
		{
			if(pred[i][0]<0) pred[i][0]=0;
			if(Math.round(pred[i][0])>Number_of_friends) pred[i][0]=Number_of_friends;
//			System.out.println((int)pred[i][0]+"           "+(int)act[i][0]);
			error+=((double)(Math.round(pred[i][0])-(int)act[i][0]))*((double)(Math.round(pred[i][0])-(int)act[i][0]));
			if(pred[i][0]-act[i][0]>10 || pred[i][0]-act[i][0]<-10)
			{
				count++;
			}
			System.out.println("Predicted likes : "+Math.round(pred[i][0]));
			System.out.println("Actual likes : "+(int)act[i][0]);
//			if(Math.round(pred[i][0])>0)
//			{
//				System.out.println("Users most likely to like this post : ");
//				List<String> print_it=WhoWillLike.getTopK((int)pred[i][0]);
//				for(String jedi:print_it)
//				{
//					System.out.println(facebookClient.fetchObject(jedi, User.class).getName());
//				}
//			}
			System.out.println("\n");
		}
		error/=n;
		System.out.println("Error with margin of 5 likes " + (count*100/n)+"%");
		System.out.println("Absolute Training error : "+error);
//		return error;
		//Hard coded training set as 100% of data
	}
}
