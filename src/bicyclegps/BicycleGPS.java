package bicyclegps;

import java.util.HashMap;

import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import map.Junction;
import map.Map;
import map.Road;

@SuppressWarnings("serial")
public class BicycleGPS extends Agent
{

	private final float VEHICLE_SPEED = 30f;
	
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
			this.distances.put(key, this.distances.get(key)/this.VEHICLE_SPEED);
		}
		
		this.setDescriptionService();
		this.addBehaviour(new BicycleItinerary());
		this.addBehaviour(new BicycleTransitTime());
	}
	
	private void setDescriptionService() 
	{
		ServiceDescription sd = new ServiceDescription();
		sd.setName("BicycleGPS");
		sd.setType("bgps");
		
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
