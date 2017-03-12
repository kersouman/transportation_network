package map;

import java.util.ArrayList;
import java.util.List;

import jade.util.leap.Serializable;

public class Section implements Serializable
{

	private List<Point> points = new ArrayList<Point>();
	private String sectionID = "";
	
	public Section(List<Point> p, String id) 
	{
		this.points = p;
		this.sectionID = id;
	}
	
	public float getLength() 
	{
		float length = 0f;
		for (int i = 0; i < this.points.size() - 1; i++)
		{
			Point c_point = this.points.get(i);
			Point n_point = this.points.get(i+1);
			length += c_point.getDistance(n_point);
		}
		return length;
	}
	
	public String getSectionID() 
	{
		return this.sectionID;
	}
	
	public boolean getByID(String id)
	{
		if (this.sectionID.equals(id))
			return true;
		return false;
	}
	
	public Point getPointHQ (String hoq)
	{
		Point phoq = null;
		if (hoq.equals("h"))
			phoq = this.points.get(0);
		if (hoq.equals("q"))
			phoq = this.points.get(this.points.size()-1);
		return phoq;
	}
	
	
}
