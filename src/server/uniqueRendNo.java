package server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * this is generating the unique random number for the client 
 *
 */
public class uniqueRendNo {
	
	private static List<Integer> ids = new ArrayList<Integer>();
	private static final int range = 100;
	private static int index = 0;
	
	
	static {
		for(int i = 0; i < range; i++)
		{
			ids.add(i);
		}
		Collections.shuffle(ids);
	}
	
	private uniqueRendNo() 
	{
		
	}
	
	public static int get_identifier()
	{
		if(index>ids.size()-1)
			index = 0;
		return ids.get(index++);
		
	}

}
