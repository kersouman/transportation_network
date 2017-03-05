package support;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import map.Junction;

public class Dijkstra
{

	private Map<Junction, Junction> 
			predecessors 	= new HashMap<Junction, Junction>();
	private Map<String, Float>
			distances 	 	= new HashMap<String, Float>();
	private List<Junction>	
			unsettledNodes	= new ArrayList<Junction>(),
			settledNodes 	= new ArrayList<Junction>();
	private List<Junction> vertices	= null;
	private Map<String, Float> locDistances = null;


	public Dijkstra(List<Junction> vertices, Map<String, Float> locDistances) 
	{
		this.vertices 		= new ArrayList<Junction>(vertices);
		this.locDistances 	= new HashMap<String, Float>(locDistances);
	}

	public void execute (Junction destination, Junction origin)
	{
		this.unsettledNodes.add(origin);
		this.distances.put(origin.getJunctionID(), 0f);

		while (this.unsettledNodes.size() > 0) 
		{
			Junction node = getMinimum(this.unsettledNodes);

			this.settledNodes.add(node);
			this.unsettledNodes.remove(node);
			this.findMinimalDistances(node);
		}
	}

	private Junction getMinimum(List<Junction> Junctions) 
	{
		Junction minimum = null;

		for (Junction junction : Junctions) 
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
		Float d = this.distances.get(destination.getJunctionID());

		if (d == null) 
		{
			d = Float.MAX_VALUE;
		} 

		return d;
	}

	private void findMinimalDistances(Junction node) 
	{
		List<Junction> adjacentNodes = getNeighbors(node);

		for (Junction target : adjacentNodes)
		{
			if (this.getShortestDistance(target) >
					this.getShortestDistance(node) + 
						this.getDistance(node, target)) 
			{
				this.distances.put(target.getJunctionID(),
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
		for (String[] section : node.getJointSections()) 
		{
			String sectionId = section[0];
			
			for (Junction junction: this.vertices)
			{
				if (junction.containSection(sectionId) && 
						!(this.isSettled(junction)))
					neighbors.add(junction);
			}
		}
		
		return neighbors;
	}

	private boolean isSettled(Junction Junction) 
	{
		return this.settledNodes.contains(Junction);
	}

	private float getDistance(Junction node, Junction target) 
	{
		String commonId = Junction.getCommonSectionId(node, target);
		return this.locDistances.get(commonId);
	}

	public ArrayList<Junction> getPath(Junction target) 
	{
		ArrayList<Junction> path = new ArrayList<Junction>();
		Junction current = target;
		
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
