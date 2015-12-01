import java.util.LinkedHashMap;

import com.restfb.FacebookClient;
import com.restfb.types.User;

public class GetLikers implements Runnable {
	
	static LinkedHashMap<String,String> people=new LinkedHashMap<String,String>();
	static LinkedHashMap<String,Long> likers;
	private FacebookClient facebookClient;
	public GetLikers(FacebookClient client) {
		facebookClient = client;
	}
	public void run() {
		people.clear();
		int ex = 0;
		String naam="";
		for(String y:likers.keySet())
		{
			if(ex>=10) break;
			naam=facebookClient.fetchObject(y, User.class).getName();
			people.put(y, naam);
			ex++;
		}
		System.out.println("Fetching complete!");
	}
}
