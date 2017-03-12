package map;

import java.io.IOException;
import java.util.HashMap;

import org.jdom2.JDOMException;

import support.JunctionsInterface;
import support.RoadsInterface;

public class Map {

	private HashMap<String, Float> lengths = new HashMap<String, Float>();
	private HashMap<String, Road> edges = new HashMap<String, Road>();
	private HashMap<String, Junction> vertices = 
			new HashMap<String, Junction>();
	
	public Map(String roads, String junctions)
			throws IOException, JDOMException
	{
		this.edges = RoadsInterface.generateRoads(roads);
		this.vertices = JunctionsInterface.generateJunctions(junctions, 
				this.edges);
		this.lengths = this.generateDistances();
	}
	
	private HashMap<String, Float> generateDistances() 
	{
		HashMap<String, Float> lengths = new HashMap<String, Float>();
		
		for (String k_road: this.edges.keySet())
		{
			HashMap<String, Section> sections = 
					this.edges.get(k_road).getSections();
			for (String k_section: sections.keySet())
			{
				lengths.put(k_section, sections.get(k_section).getLength());
			}
		}
		return lengths;
	}
	
	/*
	public List<String> getNextSections(String id)
	{
		Junction currentJunction = null;
		for (Junction junction: this.vertices) 
		{
			if (junction.getJunctionID().equals(id))
				currentJunction = junction;
		}
		List<String> nextSections = new ArrayList<String>();
		for (Object[] tabString: currentJunction.getJointSections()) 
		{
			nextSections.add(((Section)tabString[0]).getSectionID());
		}
		return nextSections;
	}
	*/
	
	public HashMap<String, Road> getEdges() 
	{
		return this.edges;
	}

	public HashMap<String, Junction> getVertices() 
	{
		return this.vertices;
	}

	public HashMap<String, Float> getLengths()
	{
		return this.lengths;
	}
	
}
