package gps;

import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate.MatchExpression;

@SuppressWarnings("serial")
public class TravelTimeRequest implements MatchExpression {

	@Override
	public boolean match(ACLMessage ttReq) 
	{
		if (ttReq.getPerformative() == ACLMessage.REQUEST && 
				ttReq.getContent().equals("Travel time request"))
		{
			return true;
		}
		return false;
	}

}
