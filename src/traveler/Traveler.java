package traveler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import map.Junction;
import support.Dijkstra;

public class Traveler extends Agent {
	
	private static int CPT_TRAVELER = 0;
	
	private HashMap<String, Object> attributes = new HashMap<String, Object>();
	
	public Traveler(Agenda agenda, Dijkstra dijkstra) {
		this.attributes.put("id", new Integer(Traveler.CPT_TRAVELER++));
		this.attributes.put("state", new Integer(0));
		this.attributes.put("agenda", agenda);
		this.attributes.put("dijkstra", dijkstra);
	}
	
	public void getShortestPath(Dijkstra dijkstra, 
			Junction origin, Junction destination) {
		dijkstra.execute(origin, destination);
		this.attributes.put("path", dijkstra.getPath(destination));
	}
	
	public void setup() {
		System.out.println("I am the traveler " + this.getAttribute("id"));
		this.setDescriptionService();
		this.setClock();
		this.addBehaviour(new TravelerTravelling());
	}
	
	private void setDescriptionService() {
		ServiceDescription sd = new ServiceDescription();
		sd.setName("Traveler" + this.attributes.get("id"));
		sd.setType("traveler");
		
		DFAgentDescription dfad = new DFAgentDescription();
		dfad.setName(this.getAID());
		dfad.addServices(sd);
		try {
			DFService.register(this, dfad);
		} catch (FIPAException e) {
			e.printStackTrace();
		}
	}
	
	private void setClock() {
		ServiceDescription sd = new ServiceDescription();
		sd.setType("clock");
		DFAgentDescription dfad = new DFAgentDescription();
		dfad.addServices(sd);
		
		try {
			DFAgentDescription[] clock = DFService.search(this, dfad);
			this.attributes.put("clock", clock[0]);
		} catch (FIPAException e) {
			e.printStackTrace();
		}
	}
	
	public Object getAttribute(String attribute) {
		return this.attributes.get(attributes);
	}
	
	public void setAttribute(String attribute, Object value) {
		this.attributes.put(attribute, value);
	}
	
}
