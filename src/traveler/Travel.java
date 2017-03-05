package traveler;

import java.io.IOException;
import java.util.Collections;

import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;

@SuppressWarnings("serial")
public class Travel extends Behaviour 
{

	@Override
	public void action() 
	{
		switch((Integer)((Traveler)myAgent).getState())
		{
		case 0:

			((Traveler)myAgent).setState(1);
			break;
		case 1:
			try 
			{
				this.gpsPathRequest();
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
			break;
		default:
			((Traveler)myAgent).setState(0);
			break;
		}
	}

	private void gpsPathRequest() 
			throws IOException
	{
		ACLMessage gpsRequest = new ACLMessage(ACLMessage.REQUEST);
		gpsRequest.addReceiver(
				((Traveler)myAgent).getGPS().get(0).getName());
		gpsRequest.setContent("Path request");
		gpsRequest.setContentObject(
				((Traveler)myAgent).getAgenda().get(
						Collections.min(
								((Traveler)myAgent).getAgenda().keySet())));
		myAgent.send(gpsRequest);		
	}
	
	/*
	 * Not the right way to do that
	 */
	private void clockRegistration() 
			throws IOException
	{
		for (int tick: ((Traveler)myAgent).getAgenda().keySet())
		{
			Object[] agendaEntry = {tick, 
					((Traveler)myAgent).getAgenda().get(tick)};
			
			ACLMessage clockRegister = new ACLMessage(ACLMessage.REQUEST);
			clockRegister.setContentObject(agendaEntry);
			clockRegister.addReceiver(
					((Traveler)myAgent).getClock().getName());
			
			myAgent.send(clockRegister);
		}
	}
	
	@Override
	public boolean done() 
	{
		return false;
	}
	
}
