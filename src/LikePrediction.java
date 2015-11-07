import java.util.ArrayList;
import java.util.List;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.types.Post;
import com.restfb.types.StatusMessage;
import com.restfb.types.User;

import Jama.Matrix;

public class LikePrediction {
	//8x1 feature vector for now
	//Offset,TimeOfDay,NumberOfFriends,PostLength,AttachedLinks,WithTag,Emoticons,Hashtags
	
	private void constructY()
	{
		//COnstructs vector of actual likes
	}
	
	private void constructX()
	{
		//Accepts a list of status messages and consturcts feature matrix
	}
	
	private double[] constructFeatures(FVector x)
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
		String MY_ACCESS_TOKEN="CAACEdEose0cBACwgjuRBaiaEkoXsfyt3SwVftZCVUVOpeOFIX9S3yaOszzSj8pxtLFw5RA7xHktCQu0friIdTsKcUVadETpRIiPuWWVwaP4al1Xi7JaZCM5K4ViPSbiB16CD3n9u4X8fD0OsKB36vJrQusiSqVeFY0njxasdfEsR6zDfVfveLaJzYj6uO7vmUPQWbosgZDZD";
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
		for(StatusMessage x:statuses)
		{
			FVector temp=new FVector();
//			System.out.println(x.getLikes().size()); //Number of likes for constructing vector 'y'
//			System.out.println(x.getMessage()); //Status message
			int hh=x.getUpdatedTime().getHours();
			int mm=x.getUpdatedTime().getMinutes();
			int time=hh*60+mm;
			int s=x.getMessage().length();
			temp.setNumber_of_friends(f);
			temp.setTime_of_day(time);
			temp.setPost_length(s);
			//Hashtag and emoticon pre-processing for some later time
			
//			System.out.println(x.getPlace()); //Check presence for location tag ; optional
			//Process message to check emoticons, length of message, hashtags 
			
		}
			
		double[][] sample={{1,5,4},{1,3,7},{7,9,4}};
		Matrix samplem=new Matrix(sample);		
		Matrix samplet=samplem.transpose();
		Matrix sampletin=samplet.times(samplem);
		Matrix sampletinv=sampletin.inverse();
		double[][] actual={{12},{147},{91}};
		Matrix actualm=new Matrix(actual);
		Matrix temp=sampletinv.times(samplet);
		Matrix theta=temp.times(actualm);
		System.out.println("Theta matrix :");
		theta.print(3, 5);
	}
}
