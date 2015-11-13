//@author : Anshuman Suri - 2014021
//@author : Satyam Kumar - 2014096

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
	private static HashMap<String, Long> mapping=new HashMap<String,Long>();
	private static long total=0;
	
	public static List<String> getTopK(int k)
	{
		Set<Entry<String, Long>> set = mapping.entrySet();
        List<Entry<String, Long>> list = new ArrayList<Entry<String, Long>>(set);
        Collections.sort( list, new Comparator<Map.Entry<String, Long>>()
        {
            public int compare( Map.Entry<String, Long> o1, Map.Entry<String, Long> o2 )
            {
                return (o2.getValue()).compareTo( o1.getValue() );
            }
        } );
        int i=0;
        List<String> ret=new ArrayList<String>();
        for(Map.Entry<String, Long> entry : list){
        	if(i>=k) break;
        	ret.add(entry.getKey());
        	i++;
        }
        return ret;
	}
	
	public static void addLike(List<NamedFacebookType> x)
	{
		long n;
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
				n=1;
				mapping.put(mnm, n);
			}
			total++;
			mapping.put(mnm,n+1);
		}
	}

	public static long getTotal() {
		return total;
	}

	public static HashMap<String, Long> getMapping() {
		return mapping;
	}	
}
