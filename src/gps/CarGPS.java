package gps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import map.Junction;
import map.Map;
import map.Road;

@SuppressWarnings("serial")
public class CarGPS extends Agent
{

	private final float CAR_SPEED = 73.9f;
	
	private List<DFAgentDescription> travelers = 
			new ArrayList<DFAgentDescription>();
	private HashMap<String, Junction> junctions = null;
	private HashMap<String, Float> distances = null;
	private HashMap<String, Road> roads = null;
	
	public void setup()
	{
		Map map = (Map)getArguments()[0];
		this.roads = map.getEdges();
		this.junctions = map.getVertices();
		this.distances = map.getLengths();
		
		for (String key: this.distances.keySet())
		{
			this.distances.put(key, this.distances.get(key)/this.CAR_SPEED);
		}
		
		System.out.println("I am the GPS");
		
		this.setDescriptionService();
		this.setMovingAgents("traveler");
		this.addBehaviour(new CarItinerary());
		this.addBehaviour(new CarTransitTime());
	}
	
	private void setDescriptionService() 
	{
		ServiceDescription sd = new ServiceDescription();
		sd.setName("CarGPS");
		sd.setType("gps");
		
		DFAgentDescription dfad = new DFAgentDescription();
		dfad.setName(this.getAID());
		dfad.addServices(sd);
		
		try 
		{
			DFService.register(this, dfad);
		} 
		catch (FIPAException e) 
		{
			e.printStackTrace();
		}
	}
	
	private void setMovingAgents(String type) 
	{
		ServiceDescription sd = new ServiceDescription();
		sd.setType(type);
		DFAgentDescription dfad = new DFAgentDescription();
		dfad.addServices(sd);
		
		try 
		{
			DFAgentDescription[] travelers = DFService.search(this, dfad);
			
			for (DFAgentDescription d: travelers) 
			{
				this.travelers.add(d);
			}
		} 
		catch (FIPAException e) 
		{
			e.printStackTrace();
		}
	}
	
	public HashMap<String, Float> getDistances()
	{
		return this.distances;
	}
	
	public float getDistance(String key)
	{
		return this.distances.get(key);
	}
	
	public HashMap<String, Road> getRoads()
	{
		return this.roads;
	}
	
	public HashMap<String, Junction> getJunctions()
	{
		return this.junctions;
	}
	
	public void setDistance(String key, Float value)
	{
		this.distances.put(key, value);
	}
	
}
