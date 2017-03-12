package map;

import java.util.HashMap;

public class Road 
{

	private String name = "";
	private HashMap<String, Section> sections = new HashMap<String, Section>();
	
	public Road(HashMap<String, Section> s, String n) 
	{
		this.name = n;
		this.sections = s;
	}
	
	public HashMap<String, Section> getSections() 
	{
		return this.sections;
	}
	
	public String getName()
	{
		return this.name;
	}
	
}
