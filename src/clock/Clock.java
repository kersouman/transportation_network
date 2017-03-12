package clock;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jade.core.AID;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

@SuppressWarnings("serial")
public class Clock extends Agent 
{

	private Map<Integer, List<AID>> startRegister =
			new HashMap<Integer, List<AID>>();
	private int tick = 0;
	private int	day  = 0;
	private int hour = 0;
	private int min  = 0;

	public void setup() 
	{
		this.tick = (int)getArguments()[0];
		this.hour = this.tick/60;
		this.min  = this.tick%60;

		this.setDescriptionService();
		this.addBehaviour(new Tick(this, 1000));
		this.addBehaviour(new StartRegister());
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
	
	public Map<Integer, List<AID>> getStartRegister()
	{
		return this.startRegister;
	}

}
