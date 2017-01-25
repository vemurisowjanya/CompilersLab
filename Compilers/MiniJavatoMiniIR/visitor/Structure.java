package visitor;

import java.util.*;

//Data structure for storing the information about variables for IR Generation

public class Structure{
	public LinkedHashMap<String,Integer> tempMapping;
	public LinkedHashMap<String,ArrayList<String>> formalParamList;
	Structure(){
		tempMapping = new LinkedHashMap<String,Integer>();
		formalParamList = new LinkedHashMap<String,ArrayList<String>>();
	}
	
}