package support;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import map.Junction;
import map.Section;

public class Dijkstra
{

	private Map<Junction, Junction> 
			predecessors 	= new HashMap<Junction, Junction>();
	private Map<Junction, Float>
			distances 	 	= new HashMap<Junction, Float>();
	private List<Junction>	
			unsettledNodes	= new ArrayList<Junction>(),
			settledNodes 	= new ArrayList<Junction>();
	private List<Junction> vertices	= null;
	private Map<String, Float> locDistances = null;

	public Dijkstra(List<Junction> vertices, Map<String, Float> locDistances) 
	{
		this.vertices 		= vertices;
		this.locDistances 	= locDistances;
		for (Junction key: this.distances.keySet())
		{
			this.distances.put(key, Float.MAX_VALUE);
		}
	}
	
	public void execute(Junction origin)
	{
		this.unsettledNodes.add(origin);
		this.distances.put(origin, 0f);
				
		System.out.println(origin);
		
		while (unsettledNodes.size() > 0) 
		{
			Junction node = this.getMinimum(this.unsettledNodes);
			System.out.println(node);
			this.settledNodes.add(node);
			for (Junction junction: this.settledNodes)
			{
				System.out.println(junction);
			}
			this.unsettledNodes.remove(node);
			this.findMinimalDistances(node);
		}
	}
	
	private Junction getMinimum(List<Junction> junctions) 
	{
		Junction minimum = null;

		for (Junction junction: junctions)
		{
			if (minimum == null)
			{
				minimum = junction;
			} 
			else
			{
				if (this.getShortestDistance(junction) < 
						this.getShortestDistance(minimum))
				{
					minimum = junction;
				}
			}
		}

		return minimum;
	}
	
	private Float getShortestDistance(Junction destination) 
	{
		Float d = this.distances.get(destination);

		if (d == null) 
		{
			d = Float.MAX_VALUE;
		} 

		return d;
	}
	
	private void findMinimalDistances(Junction node)
	{
		List<Junction> adjacentNodes = this.getNeighbors(node);

		for (Junction target : adjacentNodes)
		{
			if (this.getShortestDistance(target) > 
						this.getShortestDistance(node) + 
								this.getDistance(node, target)) 
			{
				this.distances.put(target, 
								this.getShortestDistance(node)
									+ this.getDistance(node, target));
				this.predecessors.put(target, node);
				this.unsettledNodes.add(target);
			}
		}
	}
	
	private List<Junction> getNeighbors(Junction node)
	{
		List<Junction> neighbors = new ArrayList<Junction>();

		for (Object[] section : node.getJointSections())
		{
			String sectionId = ((Section)section[0]).getSectionID();
			
			for (Junction junction: this.vertices){
				if (junction.containSection(sectionId) && 
						!(this.isSettled(junction)))
					neighbors.add(junction);
			}
		}
		return neighbors;
	}
	
	
	private boolean isSettled(Junction junction)
	{
		return this.settledNodes.contains(junction);
	}
	
	private float getDistance(Junction node, Junction target) 
	{
		float distance = 0f;
		
		if (!node.getJunctionID().equals(target.getJunctionID()))
		{
			String commonId = Junction.getCommonSectionId(node, target);
			distance = this.locDistances.get(commonId);
		}
		
		return distance;
	}

	public ArrayList<Junction> getPath(Junction target) 
	{
		ArrayList<Junction> path = new ArrayList<Junction>();
		Junction current = target;
		
		for (Junction junction: this.predecessors.keySet())
		{
			System.out.println("Key: " + junction.getJunctionID() + " " + junction);
			System.out.println("Value: " + this.predecessors.get(junction).getJunctionID());
			System.out.println(this.predecessors.get(current));
		}
		
		if (this.predecessors.get(current) == null) 
		{
			return null;
		}
		
		path.add(current);
		
		while (this.predecessors.get(current) != null) 
		{
			current = this.predecessors.get(current);
			path.add(current);
		}
		
		Collections.reverse(path);
		return path;
	}

}
