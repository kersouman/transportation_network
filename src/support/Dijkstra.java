package support;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import map.Junction;
import map.Section;

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
			Junction node = this.getMinimum(this.unsettledNodes);

			this.settledNodes.put(node.getJunctionID(), node);

			this.unsettledNodes.remove(node);
			this.findMinimalDistances(node);
		}
	}
	
	private Junction getMinimum(HashMap<String, Junction> junctions) 
	{
		Junction minimum = null;

		for (String k_junction: junctions.keySet())
		{
			if (minimum == null)
			{
				minimum = junctions.get(k_junction);
			} 
			else
			{
				if (this.getShortestDistance(junctions.get(k_junction)) < 
						this.getShortestDistance(minimum))
				{
					minimum = junctions.get(k_junction);
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
		HashMap<String, Junction> adjacentNodes = this.getNeighbors(node);

		for (String k_target: adjacentNodes.keySet())
		{
			Junction target = adjacentNodes.get(k_target);
			if (this.getShortestDistance(target) > 
						this.getShortestDistance(node) + 
								this.getDistance(node, target)) 
			{
				this.distances.put(target.getJunctionID(), 
								this.getShortestDistance(node)
									+ this.getDistance(node, target));
				this.predecessors.put(target.getJunctionID(), 
						node.getJunctionID());
				this.unsettledNodes.put(target.getJunctionID(), target);
			}
		}
	}
	
	private HashMap<String, Junction> getNeighbors(Junction node)
	{
		HashMap<String, Junction> neighbors = new HashMap<String, Junction>();

		for (Object[] section : node.getJointSections())
		{
			String sectionId = ((Section)section[0]).getSectionID();
			
			for (String k_junction: this.vertices.keySet()){
				if (this.vertices.get(k_junction).containSection(sectionId) && 
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
