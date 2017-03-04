package traveler;

import jade.core.behaviours.Behaviour;

@SuppressWarnings("serial")
public class Travel extends Behaviour 
{

	@Override
	public void action() 
	{
		switch((Integer)((Traveler)myAgent).getAttribute("state"))
		{
		case 0:
			
			break;
		}
	}

	@Override
	public boolean done() 
	{
		return false;
	}
	
}
