package map;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.jdom2.JDOMException;

import support.JunctionsInterface;
import support.RoadsInterface;

public class Map {

	private List<Road> edges = new ArrayList<Road>();
	private List<Junction> vertices = new ArrayList<Junction>();
	private HashMap<String, Float> lengths =
			new HashMap<String, Float>();
	
	public Map(String roads, String junctions)
			throws IOException, JDOMException {
		this.edges = RoadsInterface.generateRoads(roads);
		this.vertices = JunctionsInterface.generateJunctions(junctions);
		this.lengths = this.generateDistances();
	}
	
	private HashMap<String, Float> generateDistances() {
		HashMap<String, Float> lengths = 
				new HashMap<String, Float>();
		for (Road road: this.edges) {
			for (Section section: road.getSections()) {
				lengths.put(section.getSectionID(), section.getLength());
			}
		}
		return lengths;
	}
	
	public List<String> getNextSections(String id) {
		Junction currentJunction = null;
		for (Junction junction: this.vertices) {
			if (junction.getJunctionID().equals(id))
				currentJunction = junction;
		}
		List<String> nextSections = new ArrayList<String>();
		for (String[] tabString: currentJunction.getJointSections()) {
			nextSections.add(tabString[0]);
		}
		return nextSections;
	}
	
	public List<Road> getEdges() {
		return edges;
	}

	public List<Junction> getVertices() {
		return vertices;
	}

	public HashMap<String, Float> getLengths() {
		return lengths;
	}
	
}
