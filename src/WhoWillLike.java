//@author : Anshuman Suri - 2014021
//@author : Satyam Kumar - 2014096

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.restfb.types.NamedFacebookType;

public class WhoWillLike {
	private static LinkedHashMap<String,Long> mapping = new LinkedHashMap<String,Long>();
	private static long total=0;
	private static List<Entry<String, Long>> list;
	
	public static void sortIt()
	{
		Set<Entry<String, Long>> set = mapping.entrySet();
		list = new ArrayList<Entry<String, Long>>(set);
        Collections.sort( list, new Comparator<Map.Entry<String, Long>>()
        {
            public int compare( Map.Entry<String, Long> o1, Map.Entry<String, Long> o2 )
            {
                return (o2.getValue()).compareTo( o1.getValue() );
            }
        } );
        mapping.clear();
        for(Map.Entry<String, Long> x:list)
        {
        	mapping.put(x.getKey(), x.getValue());
        }
        //Order of mapping correct (as of now)
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

	public static LinkedHashMap<String, Long> getMapping() {
		return mapping;
	}	
}
