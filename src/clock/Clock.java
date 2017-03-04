package clock;

import java.util.ArrayList;
import java.util.List;

import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

@SuppressWarnings("serial")
public class Clock extends Agent 
{
	
	private List<DFAgentDescription> travelers = 
			new ArrayList<DFAgentDescription>();
	private int tick = 0;
	private int	day  = 0;
	private int hour = 0;
	private int min  = 0;
	
	public Clock() {}
	
	public void setup() 
	{
		this.setDescriptionService();
		this.setMovingAgents("traveler");
		this.addBehaviour(new Tick(this, 1000));
	}
	
	private void setDescriptionService() 
	{
		ServiceDescription sd = new ServiceDescription();
		sd.setName("Scheduler");
		sd.setType("clock");
		
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

	public List<DFAgentDescription> getTravelers() 
	{
		return travelers;
	}

	public void setTravelers(List<DFAgentDescription> travelers) 
	{
		this.travelers = travelers;
	}

	public int getTick()
	{
		return tick;
	}

	public void setTick(int tick) 
	{
		this.tick = tick;
	}

	public int getDay() 
	{
		return day;
	}

	public void setDay(int day) 
	{
		this.day = day;
	}

	public int getHour() 
	{
		return hour;
	}

	public void setHour(int hour) 
	{
		this.hour = hour;
	}

	public int getMin() 
	{
		return min;
	}

	public void setMin(int min) 
	{
		this.min = min;
	}
	
	
	
}
