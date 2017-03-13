package gps;

import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate.MatchExpression;
import jade.lang.acl.UnreadableException;

@SuppressWarnings("serial")
public class TravelTimeRequest implements MatchExpression {

	@Override
	public boolean match(ACLMessage ttReq) 
	{
		try 
		{
			if (ttReq.getPerformative() == ACLMessage.REQUEST &&
					((String[])ttReq.getContentObject())[0]
							.equals("Travel time request"))
			{
				return true;
			}
		}
		catch (UnreadableException e) {e.printStackTrace();}
		return false;
	}

}
