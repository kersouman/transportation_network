package clock;

import java.util.ArrayList;
import java.util.List;

import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

@SuppressWarnings("serial")
public class StartRegister extends CyclicBehaviour 
{

	@Override
	public void action() 
	{
		ACLMessage clockRegister = myAgent.receive(
				new MessageTemplate(new ClockRegister()));
		
		if (clockRegister == null)
		{
			block();
		}
		else
		{
			try 
			{
				this.registerTraveler(clockRegister);
			}
			catch (UnreadableException e) {e.printStackTrace();}
		}
	}
	
	private void registerTraveler(ACLMessage clockRegister) 
			throws UnreadableException
	{
		int tickStart = (int)((Object[])clockRegister.getContentObject())[1];
		
		if (((Clock)myAgent).getStartRegister().get(tickStart) == null)
		{
			List<AID> register = new ArrayList<AID>();
			register.add(clockRegister.getSender());
			((Clock)myAgent).getStartRegister().put(tickStart, register);
		}
		else
		{
			((Clock)myAgent).getStartRegister().get(tickStart).add(
					clockRegister.getSender());
		}
	}

}
