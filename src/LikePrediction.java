import Jama.Matrix;

public class LikePrediction {
	//8x1 feature vector for now
	//Offset,TimeOfDay,NumberOfFriends,PostLength,AttachedLinks,WithTag,Emoticons,Hashtags
	public static void main(String args[])
	{
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
