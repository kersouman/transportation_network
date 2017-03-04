package support;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import map.Junction;
import map.Map;

public class Dijkstra {
	
	private final List<Junction> nodes;
    private final HashMap<String, Float> lengths;
    private ArrayList<Junction> settledNodes = new ArrayList<Junction>();
    private ArrayList<Junction> unSettledNodes = new ArrayList<Junction>();
    private HashMap<Junction, Junction> predecessors = new HashMap<Junction, Junction>() ;
    private HashMap<String, Float> distance = new HashMap<String, Float>();

    public Dijkstra(Map map) {
            // create a copy of the array so that we can operate on this array
            this.nodes = map.getVertices();
            this.lengths = map.getLengths();
    }
	
	public void execute (Junction destinaire, Junction depart){
		/*Foreach node set distance[node] = HIGH
			SettledNodes = empty
			UnSettledNodes = empty
			
			Add sourceNode to UnSettledNodes
			distance[sourceNode]= 0
			
			while (UnSettledNodes is not empty) {
			        evaluationNode = getNodeWithLowestDistance(UnSettledNodes)
			        remove evaluationNode from UnSettledNodes
			    add evaluationNode to SettledNodes
			    evaluatedNeighbors(evaluationNode)
			}
			
			getNodeWithLowestDistance(UnSettledNodes){
			        find the node with the lowest distance in UnSettledNodes and return it
			}
			
			evaluatedNeighbors(evaluationNode){
			        Foreach destinationNode which can be reached via an edge from evaluationNode AND which is not in SettledNodes {
			                edgeDistance = getDistance(edge(evaluationNode, destinationNode))
			                newDistance = distance[evaluationNode] + edgeDistance
			                if (distance[destinationNode]  > newDistance ) {
			                        distance[destinationNode]  = newDistance
			                        add destinationNode to UnSettledNodes
			                }
			        }
}*/
		
		
		
		unSettledNodes.add(depart);
		distance.put(depart.getJunctionID(), (float) 0);
		
		while (unSettledNodes.size() > 0) {
            Junction node = getMinimum(unSettledNodes);
            settledNodes.add(node);
            unSettledNodes.remove(node);
            findMinimalDistances(node);
    }
		
		
	}
	
	private Junction getMinimum(List<Junction> Junctions) {
        Junction minimum = null;
        for (Junction Junction : Junctions) {
                if (minimum == null) {
                        minimum = Junction;
                } else {
                        if (getShortestDistance(Junction) < getShortestDistance(minimum)) {
                                minimum = Junction;
                        }
                }
        }
        return minimum;
	}
	
	private Float getShortestDistance(Junction destination) {
        Float d = distance.get(destination.getJunctionID());
        if (d == null) {
                return Float.MAX_VALUE;
              //For each node set distance[node] = HIGH
        } else {
                return d;
        }
	}
	
	private void findMinimalDistances(Junction node) {
        List<Junction> adjacentNodes = getNeighbors(node);
        for (Junction target : adjacentNodes) {
                if (getShortestDistance(target) > getShortestDistance(node)
                                + getDistance(node, target)) {
                        distance.put(target.getJunctionID(), getShortestDistance(node)
                                        + getDistance(node, target));
                        predecessors.put(target, node);
                        unSettledNodes.add(target);
                }
        }

	}
	
	private List<Junction> getNeighbors(Junction node) {
        List<Junction> neighbors = new ArrayList<Junction>();
        for (String[] section : node.getJointSections()) {
        	String sectionId = section[0];
        	for (Junction junction: nodes){
        		if (junction.containSection(sectionId)&&!isSettled(junction))
        			neighbors.add(junction);
        	}
        }
        return neighbors;
	}
	
	private boolean isSettled(Junction Junction) {
	        return settledNodes.contains(Junction);
	}
	
	private float getDistance(Junction node, Junction target) {
        String commonId = Junction.getCommonSectionId(node, target);
		return this.lengths.get(commonId);
	}

	public ArrayList<Junction> getPath(Junction target) {
	        ArrayList<Junction> path = new ArrayList<Junction>();
	        Junction current = target;
	        // check if a path exists
	        if (predecessors.get(current) == null) {
	                return null;
	        }
	        path.add(current);
	        while (predecessors.get(current) != null) {
	                current = predecessors.get(current);
	                path.add(current);
	        }
	        // Put it into the correct order
	        Collections.reverse(path);
	        return path;
	}
	
}
