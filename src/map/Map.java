package map;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.JDOMException;

import support.JunctionsInterface;
import support.RoadsInterface;

public class Map {

	private List<Road> edges = new ArrayList<Road>();
	private List<Junction> vertices = new ArrayList<Junction>();
	
	public Map(String roads, String junctions)
			throws IOException, JDOMException {
		this.edges = RoadsInterface.generateRoads(roads);
		this.vertices = JunctionsInterface.generateJunctions(junctions);
	}
	
}
