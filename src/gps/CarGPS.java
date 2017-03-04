package gps;

import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

@SuppressWarnings("serial")
public class CarGPS extends Agent
{

	public void setup()
	{
		this.setDescriptionService();
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
	
}
