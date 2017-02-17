package map;

import java.util.ArrayList;

public class Map {

	private ArrayList<Section> edges = new ArrayList<Section>();
	private ArrayList<Junction> vertices = new ArrayList<Junction>();
	
	public Map(ArrayList<Section> e, ArrayList<Junction> v) {
		this.edges = e;
		this.vertices = v;
	}
	
}
