package traveler;

import java.util.HashMap;

import map.Junction;

public class Agenda {

	private HashMap<Integer, Junction> schedule = 
			new HashMap<Integer, Junction>();
	
	public Agenda(HashMap<Integer, Junction> schedule) {
		this.schedule = schedule;
	}
	
	public HashMap<Integer, Junction> getSchedule() {
		return this.schedule;
	}
	
}
