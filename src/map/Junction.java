package map;

import java.util.ArrayList;
import java.util.List;

public class Junction
{
	
	private List<String[]> jointSections = new ArrayList<String[]>();
	private String junctionID = "";
	private boolean isEntry = false;
	private boolean isExit = false;
	private boolean isInterest = false;
	
	public Junction(List<String[]> j, String id)
	{
		this.jointSections = j;
		this.junctionID = id;
	}
	
	public boolean containSection(String sectionId)
	{
		for (String[] jointSection:jointSections)
		{
			if (jointSection[0].equals(sectionId))
				return true;
		}
		
		return false;
	}
	
	public static String getCommonSectionId(Junction src,Junction dest)
	{
		for(String[] sectionSrc:src.getJointSections())
		{
			for(String[] sectionDest:dest.getJointSections())
			{
				if (sectionSrc[0].equals(sectionDest[0]))
				return sectionSrc[0];
			}
		}
		
		return null;
	}
	
	public String getJunctionID()
	{
		return this.junctionID;
	}
	
	public List<String[]> getJointSections()
	{
		return this.jointSections;
	}
	
	public boolean getIsEntry()
	{
		return this.isEntry;
	}
	
	public boolean getIsExit() 
	{
		return this.isExit;
	}
	
	public boolean getIsInterest() 
	{
		return this.isInterest;
	}
}
