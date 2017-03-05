package gps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import map.Junction;
import map.Road;

@SuppressWarnings("serial")
public class CarGPS extends Agent
{

	private List<DFAgentDescription> travelers = 
			new ArrayList<DFAgentDescription>();
	private Map<String, Float> distances = null;
	private List<Junction> junctions = null;
	private List<Road> roads = null;
	
	public CarGPS(map.Map map)
	{
		this.roads = new ArrayList<Road>(map.getEdges());
		this.junctions = new ArrayList<Junction>(map.getVertices());
		this.distances = new HashMap<String, Float>(map.getLengths());
	}
	
	public void setup()
	{
		this.setDescriptionService();
		this.setMovingAgents("traveler");
		this.addBehaviour(new CarItinerary());
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
	
	public Map<String, Float> getDistances()
	{
		return this.distances;
	}
	
	public List<Road> getRoads()
	{
		return this.roads;
	}
	
	public List<Junction> getJunctions()
	{
		return this.junctions;
	}
	
	public void setDistances(Map<String, Float> distances)
	{
		this.distances = new HashMap<String, Float>(distances);
	}
	
	public void setDistance(String key, Float value)
	{
		this.distances.put(key, value);
	}
	
}
