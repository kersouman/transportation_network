package clock;

import java.util.List;
import java.util.Map;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;

@SuppressWarnings("serial")
public class Tick extends TickerBehaviour 
{

	public Tick(Agent a, long period)
	{
		super(a, period);
	}

	@Override
	protected void onTick() 
	{
		this.updateDate();
		this.setAgentOnMotion();
		System.out.println(((Clock)myAgent).getDay() + ":" +
				((Clock)myAgent).getHour() + ":" +
				((Clock)myAgent).getMin());
	}
	
	private void updateDate() 
	{
		((Clock)myAgent).setTick((((Clock)myAgent).getTick() + 1)%1440);
		int tick = ((Clock)myAgent).getTick();
		
		if (tick == 0) 
		{
			if (((Clock)myAgent).getHour() != 0) 
			{
				((Clock)myAgent).setDay(((Clock)myAgent).getDay() + 1);
			}
			((Clock)myAgent).setHour(0);
			((Clock)myAgent).setMin(0);
		} 
		else 
		{
			if (tick%60 == 0) 
			{
				((Clock)myAgent).setHour(((Clock)myAgent).getHour() + 1);
				((Clock)myAgent).setMin(0);
			} 
			else
			{
				((Clock)myAgent).setMin(((Clock)myAgent).getMin() + 1);
			}
		}
	}
	
	private void setAgentOnMotion()
	{
		Map<Integer, List<AID>> startRegister = 
				((Clock)myAgent).getStartRegister();
		List<AID> agentsToMove = startRegister.get(((Clock)myAgent).getTick());
		
		if (agentsToMove != null)
		{
			ACLMessage moveReply = new ACLMessage(ACLMessage.INFORM);
			moveReply.setContent("Start");
			
			for (AID aid: agentsToMove)
			{
				moveReply.addReceiver(aid);
			}
			
			myAgent.send(moveReply);
		}
	}
	
}
