package map;

import java.util.ArrayList;
import java.util.List;

public class Road {

	private String name = "";
	private List<Section> sections = new ArrayList<Section>();
	
	public Road(List<Section> s, String n) {
		this.name = n;
		this.sections = s;
	}
	
}
