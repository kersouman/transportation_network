package gps;

import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate.MatchExpression;

@SuppressWarnings("serial")
public class CarGPSRequest implements MatchExpression 
{

	@Override
	public boolean match(ACLMessage req) 
	{		
		if (req.getPerformative() == ACLMessage.REQUEST)
		{
			return true;
		}
		
		return false;
	}

}
