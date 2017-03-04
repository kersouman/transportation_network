package traveler;

import java.io.IOException;

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
			try 
			{
				this.clockRegistration();
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
			((Traveler)myAgent).setState(1);
			break;
		case 1:
			
			break;
		default:
			((Traveler)myAgent).setState(0);
			break;
		}
	}

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
