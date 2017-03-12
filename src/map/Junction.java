package map;

import java.util.ArrayList;
import java.util.List;

import jade.util.leap.Serializable;

@SuppressWarnings("serial")
public class Junction implements Serializable
{
	
	private List<Object[]> jointSections = new ArrayList<Object[]>();
	//list of Object[] where Object[] contains Section at 0 and hoq at 1
	private String junctionID = "";
	private boolean isEntry = false;
	private boolean isExit = false;
	private boolean isInterest = false;
	
	public Junction(List<Object[]> j, String id) 
	{
		this.jointSections = j;
		this.junctionID = id;
	}
	
	public String getJunctionID() 
	{
		return this.junctionID;
	}
	
	public List<Object[]> getJointSections() 
	{
		return this.jointSections;
	}
	
	public boolean containSection(String sectionId)
	{
		for (Object[] jointSection:jointSections)
		{
			if (((Section)jointSection[0]).getSectionID().equals(sectionId))
				return true;
		}
		
		return false;
	}
	
	public static String getCommonSectionId(Junction src,Junction dest)
	{
		for (Object[] sectionSrc:src.getJointSections())
		{
			for (Object[] sectionDest:dest.getJointSections())
			{
				if (((Section)sectionSrc[0]).getSectionID().equals(
						((Section)sectionDest[0]).getSectionID()))
					return ((Section)sectionSrc[0]).getSectionID();
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
	
	public Point getMilieu()
	{
		List<Point> points = new ArrayList<Point>();
		for (Object[] sectionJoint:this.getJointSections())
		{
			points.add(((Section)sectionJoint[0]).getPointHQ(
					(String)sectionJoint[1]));
	
		}
	
		int x = 0;
		int y = 0;
		for (Point point: points)
		{
			x += point.getCoordX();
			y += point.getCoordY();
		}
		
		x = (int) x/points.size();
		y = (int) y/points.size();
		return new Point(x,y);
	}
}
