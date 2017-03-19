package bicycle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jade.core.AID;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import map.Junction;
import map.Map;

@SuppressWarnings("serial")
public class Bicycle extends Agent 
{
	
	private static int CPT_TRAVELER = 0;

	private Map map = null;
	
	private List<String> path = new ArrayList<String>();
	private HashMap<Integer, Junction[]> agenda = null;
	private AID clock = null;
	private AID gps = null;
	private String currentJunction = "";
	private int totalTimeTravel = 0;
	private int margin = 0;
	private int state = -1;
	private int id = -1;

	@SuppressWarnings("unchecked")
	public void setup() 
	{
		this.id = Bicycle.CPT_TRAVELER++;
		this.state = 0;
		
		this.agenda = (HashMap<Integer, Junction[]>)getArguments()[0];
		this.map = (Map)getArguments()[1];
		this.margin = (int)getArguments()[2];
		
		this.setDescriptionService();
		this.setClock();
		this.setGPS();
		this.addBehaviour(new Travel());
	}
	
	private void setDescriptionService() 
	{
		ServiceDescription sd = new ServiceDescription();
		sd.setName("Car" + this.id);
		sd.setType("traveler");
		
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
	
	private void setClock() 
	{
		ServiceDescription sd = new ServiceDescription();
		sd.setType("clock");
		DFAgentDescription dfad = new DFAgentDescription();
		dfad.addServices(sd);
		
		try 
		{
			DFAgentDescription[] clock = DFService.search(this, dfad);
			this.clock = clock[0].getName();
		} 
		catch (FIPAException e) 
		{
			e.printStackTrace();
		}
	}
	
	private void setGPS()
	{
		ServiceDescription sd = new ServiceDescription();
		sd.setType("bgps");
		DFAgentDescription dfad = new DFAgentDescription();
		dfad.addServices(sd);
		
		try
		{
			DFAgentDescription[] gps = DFService.search(this, dfad);
			this.gps = gps[0].getName();
		}
		catch (FIPAException e)
		{
			e.printStackTrace();
		}
	}

	public List<String> getPath() 
	{
		return this.path;
	}

	public void setPath(List<String> path)
	{
		this.path = path;
	}

	public int getState() 
	{
		return this.state;
	}

	public void setState(int state)
	{
		this.state = state;
	}

	public HashMap<Integer, Junction[]> getAgenda() 
	{
		return this.agenda;
	}

	public AID getClock() 
	{
		return this.clock;
	}

	public int getId()
	{
		return this.id;
	}
	
	public AID getGPS()
	{
		return this.gps;
	}
	
	public String getCurrentJunction()
	{
		return this.currentJunction;
	}
	
	public void setCurrentJunction(String currentJunction)
	{
		this.currentJunction = currentJunction;
	}
	
	public int getTotalTimeTravel()
	{
		return this.totalTimeTravel;
	}
	
	public void setTotalTimeTravel(int totalTimeTravel)
	{
		this.totalTimeTravel = totalTimeTravel;
	}
	
	public int getMargin()
	{
		return this.margin;
	}
	
	public Map getMap()
	{
		return this.map;
	}
	
}
