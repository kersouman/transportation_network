package map;

import java.util.HashMap;

import jade.util.leap.Serializable;

@SuppressWarnings("serial")
public class Junction implements Serializable
{
	
	private HashMap<String, String> jointSections = 
			new HashMap<String, String>();
	//list of Object[] where Object[] contains Section at 0 and hoq at 1
	private String junctionID = "";
	
	public Junction(HashMap<String, String> j, String id) 
	{
		this.jointSections = j;
		this.junctionID = id;
	}
	
	public String getJunctionID() 
	{
		return this.junctionID;
	}
	
	public HashMap<String, String> getJointSections() 
	{
		return this.jointSections;
	}
	
	public boolean containSection(String sectionId)
	{
		for (String jointSection: this.jointSections.keySet())
		{
			if (jointSection.equals(sectionId))
				return true;
		}
		
		return false;
	}
	
	public static String getCommonSectionId(Junction j1, Junction j2)
	{
		for (String section1: j1.getJointSections().keySet())
		{
			for (String section2: j2.getJointSections().keySet())
			{
				if (section1.equals(section2))
					return section1;
			}
		}
		
		return null;
	}
	
	public boolean getByID(String id)
	{
		if (this.junctionID.equals(id))
			return true;
		return false;
	}
}
