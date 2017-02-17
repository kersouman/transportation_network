package map;

import java.util.ArrayList;

public class Road {

	private String name = "";
	private int sectionID = 0;
	private ArrayList<Point> points = new ArrayList<Point>();
	
	public Road(String n, int id, ArrayList<Point> p) {
		this.name = n;
		this.sectionID = id;
		this.points = p;
	}
	
	public float getLength() {
		float length = 0f;
		for (int i = 0; i < this.points.size() - 1; i++) {
			Point c_point = this.points.get(i);
			Point n_point = this.points.get(i+1);
			length += c_point.getDistance(n_point);
		}
		return length;
	}
}
