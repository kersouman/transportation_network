package gps;

import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate.MatchExpression;

@SuppressWarnings("serial")
public class CarGPSRequest implements MatchExpression 
{

	@Override
	public boolean match(ACLMessage req) 
	{
		boolean match = false;
		
		if (req.getPerformative() == ACLMessage.REQUEST && 
				req.getContent().equals("Path request"))
		{
			match = true;
		}
		
		return match;
	}

}
