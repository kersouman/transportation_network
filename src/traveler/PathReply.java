package traveler;

import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate.MatchExpression;

@SuppressWarnings("serial")
public class PathReply implements MatchExpression 
{

	@Override
	public boolean match(ACLMessage rep) 
	{
		if (rep.getPerformative() == ACLMessage.AGREE)
		{
			return true;
		}
		
		return false;
	}

}
