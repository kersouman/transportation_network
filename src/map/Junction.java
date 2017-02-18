package map;

import java.util.ArrayList;
import java.util.List;

public class Junction {

	private List<String[]> jointSections = new ArrayList<String[]>();
	private String junctionID = "";
	
	public Junction(List<String[]> j, String id) {
		this.jointSections = j;
		this.junctionID = id;
	}
	
}
