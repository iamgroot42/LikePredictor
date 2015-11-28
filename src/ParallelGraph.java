import java.util.ArrayList;
import com.restfb.types.Post;

public class ParallelGraph implements Runnable {
	private static ArrayList<Post> data=new ArrayList<Post>();
	public ParallelGraph(ArrayList<Post> x)
	{
		ParallelGraph.data=x;
	}
	public static void killIt()
	{
		data.clear();
		System.out.println("Parallel Graph data cleared");
	}
	public void run()
	{		
		for(Post x:ParallelGraph.data)
		{
			if(x.getLikesCount()!=null)
			{
				if(x.getLikes().getData()!=null) WhoWillLike.addLike(x.getLikes().getData());
			}
		}
	}
}
