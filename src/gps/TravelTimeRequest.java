package gps;

import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate.MatchExpression;

@SuppressWarnings("serial")
public class TravelTimeRequest implements MatchExpression {

	@Override
	public boolean match(ACLMessage ttReq) 
	{
		if (ttReq.getPerformative() == ACLMessage.REQUEST)
		{
			return true;
		}
		return false;
	}

}
