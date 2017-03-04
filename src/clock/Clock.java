package clock;

import java.util.ArrayList;
import java.util.HashMap;

import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

public class Clock extends Agent {

	private HashMap<String, Object> attributes =
			new HashMap<String, Object>();
	
	public Clock() {
		this.attributes.put("tick", new Integer(0));
		this.attributes.put("day", new Integer(0));
		this.attributes.put("hour", new Integer(0));
		this.attributes.put("minute", new Integer(0));
		this.attributes.put("travelers", new ArrayList<DFAgentDescription>());
	}
	
	public void setup() {
		this.setDescriptionService();
		this.setMovingAgents("traveler");
		this.addBehaviour(new ClockTicker(this, 1000));
	}
	
	private void setDescriptionService() {
		ServiceDescription sd = new ServiceDescription();
		sd.setName("Scheduler");
		sd.setType("clock");
		
		DFAgentDescription dfad = new DFAgentDescription();
		dfad.setName(this.getAID());
		dfad.addServices(sd);
		try {
			DFService.register(this, dfad);
		} catch (FIPAException e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	private void setMovingAgents(String type) {
		ServiceDescription sd = new ServiceDescription();
		sd.setType(type);
		DFAgentDescription dfad = new DFAgentDescription();
		dfad.addServices(sd);
		
		try {
			DFAgentDescription[] travelers = DFService.search(this, dfad);
			for (DFAgentDescription d: travelers) {
				((ArrayList<DFAgentDescription>)this.attributes.
						get("travelers")).add(d);
			}
		} catch (FIPAException e) {
			e.printStackTrace();
		}
	}
	
	public Object getAttribute(String attribute) {
		return this.attributes.get(attribute);
	}
	
	public void setAttribute(String attribute, Object value) {
		this.attributes.put(attribute, value);
	}
	
}
