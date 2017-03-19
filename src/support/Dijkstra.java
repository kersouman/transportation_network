package support;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import map.Junction;

public class Dijkstra
{

	private HashMap<String, String> 
			predecessors 	= new HashMap<String, String>();
	private HashMap<String, Float>
			distances 	 	= new HashMap<String, Float>();
	private HashMap<String, Junction>	
			unsettledNodes	= new HashMap<String, Junction>(),
			settledNodes 	= new HashMap<String, Junction>();
	private HashMap<String, Junction> vertices	= null;
	private HashMap<String, Float> locDistances = null;

	public Dijkstra(HashMap<String, Junction> vertices, 
			HashMap<String, Float> locDistances) 
	{
		this.vertices 		= vertices;
		this.locDistances 	= locDistances;
		for (String key: this.distances.keySet())
		{
			this.distances.put(key, Float.MAX_VALUE);
		}
	}
	
	public void execute(String origin_id)
	{
		this.unsettledNodes.put(origin_id, this.vertices.get(origin_id));
		this.distances.put(origin_id, 0f);
		
		while (unsettledNodes.size() > 0) 
		{
			String node = this.getMinimum(this.unsettledNodes);
			
			this.settledNodes.put(node, this.vertices.get(node));	
			this.unsettledNodes.remove(node);
			this.findMinimalDistances(node);
		}
	}
	
	private String getMinimum(HashMap<String, Junction> junctions) 
	{
		String minimum = null;

		for (String k_junction: junctions.keySet())
		{
			if (minimum == null)
			{
				minimum = k_junction;
			} 
			else
			{
				if (this.getShortestDistance(k_junction) < 
						this.getShortestDistance(minimum))
				{
					minimum = k_junction;
				}
			}
		}

		return minimum;
	}
	
	private Float getShortestDistance(String destination) 
	{
		Float d = this.distances.get(destination);

		if (d == null) 
		{
			d = Float.MAX_VALUE;
		} 

		return d;
	}
	
	private void findMinimalDistances(String node)
	{
		HashMap<String, Junction> adjacentNodes = this.getNeighbors(node);

		for (String k_target: adjacentNodes.keySet())
		{
			String target = k_target;
			if (this.getShortestDistance(k_target) > 
						this.getShortestDistance(node) + 
								this.getDistance(node, target)) 
			{
				this.distances.put(target, 
								this.getShortestDistance(node)
									+ this.getDistance(node, target));
				this.predecessors.put(target, node);
				this.unsettledNodes.put(target, this.vertices.get(target));
			}
		}
	}
	
	private HashMap<String, Junction> getNeighbors(String node)
	{
		HashMap<String, Junction> neighbors = new HashMap<String, Junction>();
		for (String section : 
			this.vertices.get(node).getJointSections().keySet())
		{
			for (String k_junction: this.vertices.keySet())
			{
				if (this.vertices.get(k_junction).containSection(section) && 
						!(this.isSettled(k_junction)))
					neighbors.put(this.vertices.get(k_junction).getJunctionID(),
							this.vertices.get(k_junction));
			}
		}
		return neighbors;
	}
	
	private boolean isSettled(String junction)
	{
		return this.settledNodes.containsKey(junction);
	}
	
	private float getDistance(String node, String target) 
	{
		float distance = 0f;
		
		if (!node.equals(target))
		{
			String commonId = Junction.getCommonSectionId(
					this.vertices.get(node), this.vertices.get(target));
			distance = this.locDistances.get(commonId);
		}
		
		return distance;
	}

	public ArrayList<String> getPath(String target) 
	{
		ArrayList<String> path = new ArrayList<String>();
		String current = target;
				
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
