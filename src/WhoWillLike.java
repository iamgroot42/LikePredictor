import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.restfb.types.NamedFacebookType;

public class WhoWillLike {
	private static HashMap<String, Integer> mapping=new HashMap<String,Integer>();
	
	public static List<String> getTopK(int k)
	{
		Set<Entry<String, Integer>> set = mapping.entrySet();
        List<Entry<String, Integer>> list = new ArrayList<Entry<String, Integer>>(set);
        Collections.sort( list, new Comparator<Map.Entry<String, Integer>>()
        {
            public int compare( Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2 )
            {
                return (o2.getValue()).compareTo( o1.getValue() );
            }
        } );
        int i=0;
        List<String> ret=new ArrayList<String>();
        for(Map.Entry<String, Integer> entry : list){
        	if(i>=k) break;
        	ret.add(entry.getKey());
        	i++;
        }
        return ret;
	}
	
	public static void addLike(List<NamedFacebookType> x)
	{
		int n;
		String mnm;
		for(NamedFacebookType y : x)
		{
			mnm=y.getId();
			if(mapping.containsKey(mnm))
			{
				n=mapping.get(mnm);
			}
			else
			{
				mapping.put(mnm, 1);
				n=1;
			}
			mapping.put(mnm,n+1);
		}
	}
	
	public static void printAll()
	{
		for(String x:mapping.keySet())
		{
			System.out.println(x+" : "+mapping.get(x));
		}
	}
}
