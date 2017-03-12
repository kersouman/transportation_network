package traveler;

import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate.MatchExpression;

@SuppressWarnings("serial")
public class TravelTimeReply implements MatchExpression 
{

	@Override
	public boolean match(ACLMessage ttRep) 
	{
		if (ttRep.getPerformative() == ACLMessage.INFORM)
		{
			return true;
		}
		return false;
	}

}