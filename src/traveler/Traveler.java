package traveler;

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

@SuppressWarnings("serial")
public class Traveler extends Agent 
{
	
	private static int CPT_TRAVELER = 0;

	private List<DFAgentDescription> gps = new ArrayList<DFAgentDescription>();
	private List<Junction> path = new ArrayList<Junction>();
	private Map<Integer, Junction[]> agenda = null;
	private DFAgentDescription clock = null;
	private int state = -1;
	private int id = -1;

	public Traveler(HashMap<Integer, Junction[]> agenda) 
	{
		this.id 		= Traveler.CPT_TRAVELER++;
		this.state 		= 0;
		this.agenda		= new HashMap<Integer, Junction[]>(agenda);
	}
	
	public void setup() 
	{
		System.out.println("I am the traveler " + this.id);
		this.setDescriptionService();
		this.setClock();
		this.setGPS();
		this.addBehaviour(new Travel());
	}
	
	private void setDescriptionService() 
	{
		ServiceDescription sd = new ServiceDescription();
		sd.setName("Traveler" + this.id);
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
			this.clock = clock[0];
		} 
		catch (FIPAException e) 
		{
			e.printStackTrace();
		}
	}
	
	private void setGPS()
	{
		ServiceDescription sd = new ServiceDescription();
		sd.setType("gps");
		DFAgentDescription dfad = new DFAgentDescription();
		dfad.addServices(sd);
		
		try
		{
			DFAgentDescription[] gps = DFService.search(this, dfad);
		
			for (DFAgentDescription g: gps)
			{
				this.gps.add(g);
			}
		}
		catch (FIPAException e)
		{
			e.printStackTrace();
		}
	}

	public List<Junction> getPath() 
	{
		return this.path;
	}

	public void setPath(List<Junction> path)
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

	public Map<Integer, Junction[]> getAgenda() 
	{
		return this.agenda;
	}

	public DFAgentDescription getClock() 
	{
		return this.clock;
	}

	public int getId()
	{
		return this.id;
	}
	
	public List<DFAgentDescription> getGPS()
	{
		return this.gps;
	}
	
}
